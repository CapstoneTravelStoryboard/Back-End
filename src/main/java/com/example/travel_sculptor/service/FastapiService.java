package com.example.travel_sculptor.service;

import com.example.travel_sculptor.domain.Landmark;
import com.example.travel_sculptor.dto.fastapi.IotroFastapiResponseDTO;
import com.example.travel_sculptor.dto.fastapi.TitleFastapiRequestDTO;
import com.example.travel_sculptor.dto.recommend.TitleRequestDTO;
import com.example.travel_sculptor.repository.LandmarkRepository;
import com.example.travel_sculptor.repository.StoryboardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FastapiService {

    private final StoryboardRepository storyboardRepository;
    private final LandmarkRepository landmarkRepository;

    private final WebClient webClient;

    @Autowired
    public FastapiService(StoryboardRepository storyboardRepository, LandmarkRepository landmarkRepository, WebClient.Builder webClientBuilder, @Value("${fastapi.server.url}") String serverUrl) {
        this.storyboardRepository = storyboardRepository;
        this.landmarkRepository = landmarkRepository;
        this.webClient = webClientBuilder.baseUrl(serverUrl).build();
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
                .uri("/recommend/title") // FastAPI 엔드포인트 URI
                .bodyValue(titleFastapiRequestDTO) // 요청 body에 DTO 전달
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                }); // 응답 타입 매핑

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
                .uri("/recommend/iotro") // FastAPI 엔드포인트 URI
                .bodyValue(title) // 요청 body에 DTO 전달
                .retrieve()
                .bodyToMono(IotroFastapiResponseDTO.class); // 응답 타입 매핑

        // 결과 반환
        return response.block(); // 동기 방식으로 결과를 반환 (비동기 원한다면 변경 가능)
    }
}
