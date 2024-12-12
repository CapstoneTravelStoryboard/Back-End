package com.example.travel_sculptor.service;

import com.example.travel_sculptor.domain.*;
import com.example.travel_sculptor.dto.fastapi.*;
import com.example.travel_sculptor.dto.recommend.TitleRequestDTO;
import com.example.travel_sculptor.dto.storyboard.StoryboardCreateRequestDTO;
import com.example.travel_sculptor.dto.storyboard.StoryboardCreateResponseDTO;
import com.example.travel_sculptor.repository.LandmarkRepository;
import com.example.travel_sculptor.repository.MemberRepository;
import com.example.travel_sculptor.repository.StoryboardRepository;
import com.example.travel_sculptor.repository.TripRepository;
import com.example.travel_sculptor.util.SceneDescriptionGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FastapiService {

    private final StoryboardRepository storyboardRepository;
    private final LandmarkRepository landmarkRepository;
    private final MemberRepository memberRepository;
    private final TripRepository tripRepository;

    private final WebClient webClient;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Autowired
    public FastapiService(StoryboardRepository storyboardRepository, LandmarkRepository landmarkRepository, MemberService memberService,
                          WebClient.Builder webClientBuilder, @Value("${fastapi.server.url}") String serverUrl, MemberRepository memberRepository, TripRepository tripRepository) {
        this.storyboardRepository = storyboardRepository;
        this.landmarkRepository = landmarkRepository;
        this.webClient = webClientBuilder.baseUrl(serverUrl).build();
        this.memberRepository = memberRepository;
        this.tripRepository = tripRepository;
    }


    /***
     * 스토리보드 제목 추천
     * @param titleRequestDTO
     * @return 추천 제목 리스트 (문자열 리스트)
     */
    public List<String> recommendTitle(TitleRequestDTO titleRequestDTO) {

        Optional<Landmark> landmark = landmarkRepository.findById(titleRequestDTO.getLandmarkId());
        if (landmark.isEmpty()) {
            throw new IllegalArgumentException("Landmark not found");
        }

        // Landmark 정보를 TitleFastapiDTO에 추가
        TitleFastapiRequestDTO titleFastapiRequestDTO = TitleFastapiRequestDTO.builder()
                .destination(landmark.get().getName())
                .description(landmark.get().getDescription())
                .purpose(titleRequestDTO.getPurpose())
                .companions(titleRequestDTO.getCompanions())
                .companionCount(titleRequestDTO.getCompanionCount())
                .season(titleRequestDTO.getSeason())
                .build();


        // FastAPI 서버로 요청 전송
        Mono<List<String>> response = webClient.post()
                .uri("/recommend/titles") // FastAPI 엔드포인트 URI
                .bodyValue(titleFastapiRequestDTO) // 요청 body에 DTO 전달
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                }); // 응답 타입 매핑

//        List<String> block = response.block();
//        System.out.println("응답 결과 출력");
//        for (String str : block) {
//            System.out.println(str);
//        }
//
//        return block;
        // 결과 반환
        return response.block(); // 동기 방식으로 결과를 반환 (비동기 원한다면 변경 가능)
    }


    /***
     * 스토리보드 인트로 & 아웃트로 추천
     * @param title
     * @return
     */
    public IotroFastapiResponseDTO recommendIntroOutro(String title) {
        // FastAPI 서버로 요청 전송
        Mono<IotroFastapiResponseDTO> response = webClient.post()
                .uri("/recommend/iotros") // FastAPI 엔드포인트 URI
                .bodyValue(title) // 요청 body에 DTO 전달
                .retrieve()
                .bodyToMono(IotroFastapiResponseDTO.class); // 응답 타입 매핑

        // 결과 반환
        return response.block(); // 동기 방식으로 결과를 반환 (비동기 원한다면 변경 가능)
    }


    /***
     * 스토리보드 생성 요청
     * @param storyboardCreateRequestDTO
     * @return
     */
    public StoryboardCreateResponseDTO createStoryboard(Long tripId, StoryboardCreateRequestDTO storyboardCreateRequestDTO) {

        // 현재 로그인한 사용자 정보 조회
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Optional<Trip> trip = tripRepository.findById(tripId);
        if (trip.isEmpty()) {
            throw new IllegalArgumentException("Trip not found");
        }


        // Landmark 정보를 StoryboardFastapiDTO에 추가
        Optional<Landmark> landmarkOptional = landmarkRepository.findById(storyboardCreateRequestDTO.getLandmarkId());
        if (landmarkOptional.isEmpty()) {
            throw new IllegalArgumentException("Landmark not found");
        }

        Landmark landmark = landmarkOptional.get();

        String destination = landmark.getName();
        String description = landmark.getDescription();

        // 이미지 URL 생성
        List<String> imageUrls = getImageUrls(landmark, destination);

        StoryboardFastapiRequestDTO requestDTO = StoryboardFastapiRequestDTO.builder()
                .destination(destination)
                .purpose(storyboardCreateRequestDTO.getPurpose())
                .companions(storyboardCreateRequestDTO.getCompanions())
                .companionCount(storyboardCreateRequestDTO.getCompanionCount())
                .season(storyboardCreateRequestDTO.getSeason())
                .title(storyboardCreateRequestDTO.getTitle())
                .intro(storyboardCreateRequestDTO.getIntro())
                .outro(storyboardCreateRequestDTO.getOutro())
                .description(description)
                .imageUrls(imageUrls)
                .build();


        // FastAPI 서버로 요청 전송
        Mono<StoryboardFastapiResponseDTO> response = webClient.post()
                .uri("/fastapi/storyboards") // FastAPI 엔드포인트 URI
                .bodyValue(requestDTO) // 요청 body에 DTO 전달
                .retrieve()
                .bodyToMono(StoryboardFastapiResponseDTO.class); // 응답 타입 매핑

        // // 동기 방식으로 결과를 반환
        StoryboardFastapiResponseDTO storyboardFastapiResponseDTO = response.block();

        if (storyboardFastapiResponseDTO == null || storyboardFastapiResponseDTO.getStoryboardScenes().isEmpty()) {
            throw new IllegalStateException("No scenes returned from FastAPI");
        }

        //Storyboard 엔티티 생성
        Storyboard storyboard = Storyboard.builder()
                .member(member)
                .trip(trip.get())
                .title(storyboardCreateRequestDTO.getTitle())
                .landmark(landmark)
                .companions(storyboardCreateRequestDTO.getCompanions())
                .companionCount(storyboardCreateRequestDTO.getCompanionCount())
                .purpose(storyboardCreateRequestDTO.getPurpose())
                .startDate(storyboardCreateRequestDTO.getStartDateTime())
                .endDate(storyboardCreateRequestDTO.getEndDateTime())
                .intro(storyboardCreateRequestDTO.getIntro())
                .outro(storyboardCreateRequestDTO.getOutro())
                .build();

        //Scene 엔티티 생성 및 Storyboard와 연관 설정
        for (StoryboardFastapiResponseDTO.SceneDTO sceneDTO : storyboardFastapiResponseDTO.getStoryboardScenes()) {
            Scene scene = Scene.builder()
                    .orderNum(sceneDTO.getOrderNum())
                    .title(sceneDTO.getSceneTitle())
                    .description(sceneDTO.getDescription())
                    .cameraAngle(sceneDTO.getCameraAngle())
                    .cameraMovement(sceneDTO.getCameraMovement())
                    .composition(sceneDTO.getComposition())
                    .build();
            storyboard.addScene(scene); // 연관관계 편의 메서드 호출
        }

        //Storyboard 저장 (Scene은 cascade 설정으로 자동 저장)
        Storyboard savedStoryboard = storyboardRepository.save(storyboard);

        //trip의 dayStart 업데이트 후 DB에 저장
        trip.get().updateDayStart(storyboardCreateRequestDTO.getStartDateTime());


        // 프론트로 응답할 DTO 생성
        // storyboardFastapiResponseDTO -> StoryboardCreateResponseDTO 변환
        StoryboardCreateResponseDTO createResponseDTO =
                StoryboardCreateResponseDTO.builder()
                        .id(savedStoryboard.getId())
                        .storyboardScenes(
                                storyboardFastapiResponseDTO.getStoryboardScenes().stream()
                                        .map(sceneDTO -> {
                                            StoryboardCreateResponseDTO.SceneSummaryDTO summaryDTO =
                                                    new StoryboardCreateResponseDTO.SceneSummaryDTO();
                                            summaryDTO.setOrderNum(sceneDTO.getOrderNum());
                                            summaryDTO.setSceneTitle(sceneDTO.getSceneTitle());
                                            summaryDTO.setDescription(sceneDTO.getDescription());
                                            return summaryDTO;
                                        })
                                        .collect(Collectors.toList())
                        )
                        .build();


        return createResponseDTO;

    }

    /***
     * 스토리보드 장면 별 이미지 생성 요청
     * @param storyboardId
     * @param season
     * @throws Exception
     */
    public void createImages(Long storyboardId, String season) throws Exception {

        Optional<Storyboard> byId = storyboardRepository.findById(storyboardId);
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("Storyboard not found");
        }

        Storyboard storyboard = byId.get();
        List<Scene> scenes = storyboard.getScenes();
        Landmark landmark = storyboard.getLandmark();

        SceneDescriptionGenerator generator = new SceneDescriptionGenerator();

        List<String> sceneDescriptions = generator.generateSceneDescriptions(scenes);

        List<ImageFastapiRequestDTO> imageFastapiRequestDTOs = new ArrayList<>();

        for (int i = 0; i < scenes.size(); i++) {
            Scene scene = scenes.get(i);
            String sceneDescription = sceneDescriptions.get(i);

            // 테스트 출력용
            System.out.println("Scene orderNum: " + scene.getOrderNum() + ", Description: " + sceneDescription);

            ImageFastapiRequestDTO requestDTO = ImageFastapiRequestDTO.builder()
                    .sceneDescription(sceneDescription)
                    .storyboardId(storyboardId)
                    .destination(storyboard.getLandmark().getName())
                    .purpose(storyboard.getPurpose())
                    .companion(storyboard.getCompanions())
                    .companionCount(storyboard.getCompanionCount())
                    .season(season)
                    .orderNum(scene.getOrderNum()) // orderNum 추가
                    .imageUrls(getImageUrls(landmark, landmark.getName()))
                    .build();

            imageFastapiRequestDTOs.add(requestDTO);
        }

        // 장면 별 ImageFastapiRequestDTO 생성
//        for (String sceneDescription : sceneDescriptions) {
//            //테스트 출력용
//            System.out.println(sceneDescription);
//
//            ImageFastapiRequestDTO requestDTO = ImageFastapiRequestDTO.builder()
//                    .sceneDescription(sceneDescription)
//                    .storyboardId(storyboardId)
//                    .destination(storyboard.getLandmark().getName())
//                    .purpose(storyboard.getPurpose())
//                    .companion(storyboard.getCompanions())
//                    .companionCount(storyboard.getCompanionCount())
//                    .season(season)
//                    .imageUrls(getImageUrls(landmark, landmark.getName()))
//                    .build();
//
//            imageFastapiRequestDTOs.add(requestDTO);
//        }

        // FastAPI 서버로 요청 전송
        Mono<String> response = webClient.post()
                .uri("/fastapi/images") // FastAPI 엔드포인트 URI
                .bodyValue(imageFastapiRequestDTOs) // 요청 body에 DTO 전달
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<String>() {
                });// 응답 타입 매핑

        String responseMessage = response.block();// 동기 방식으로 결과를 반환 (비동기 원한다면 변경 가능)

        //테스트 출력용
        try {
            System.out.println(responseMessage);
        } catch (NullPointerException e) {
            System.out.println("No response from FastAPI");
        }

    }

    private List<String> getImageUrls(Landmark landmark, String destination) {
        String province = landmark.getRegion().getProvince();
        String district = landmark.getRegion().getDistrict();

        // Landmark 정보 기반으로 S3에 저장된 이미지 URL 만들기
        // 경로 생성 ex) s3://travel-sculptor/images/landmark/서울/종로구/경복궁
        String basePath = String.format("https://%s.s3.ap-northeast-2.amazonaws.com/images/landmark/%s/%s/%s",
                bucketName, province, district, destination);

        // 결과 URI 생성
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(basePath + "_1.jpg");
        imageUrls.add(basePath + "_2.jpg");
        return imageUrls;
    }


}
