package com.example.travel_sculptor.service;

import com.example.travel_sculptor.domain.*;
import com.example.travel_sculptor.dto.scene.SceneSummaryDTO;
import com.example.travel_sculptor.dto.storyboard.StoryboardSummaryDTO;
import com.example.travel_sculptor.dto.storyboard.StoryboardUpdateDTO;
import com.example.travel_sculptor.repository.MemberRepository;
import com.example.travel_sculptor.repository.StoryboardRepository;
import com.example.travel_sculptor.repository.TripRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryboardService {

    private final StoryboardRepository storyboardRepository;
    private final TripRepository tripRepository;
    private final MemberRepository memberRepository;


    /***
     * 스토리보드 조회
     * @param tripId
     * @return
     */
    public List<StoryboardSummaryDTO> getStoryboards(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        List<Storyboard> storyboards = trip.getStoryboards();

        List<StoryboardSummaryDTO> storyboardSummaryDTOList = new ArrayList<>();

        // StoryboardSummaryDTO로 변환
        for (Storyboard storyboard : storyboards) {
            Landmark landmark = storyboard.getLandmark();
            String province = landmark.getRegion().getProvince();
            String district = landmark.getRegion().getDistrict();
            String name = landmark.getName();

            // province, district, name을 합쳐서 landmarkInfo 생성
            String landmarkInfo = String.format("%s %s %s", province, district, name);

            storyboardSummaryDTOList.add(StoryboardSummaryDTO.builder()
                    .id(storyboard.getId())
                    .title(storyboard.getTitle())
                    .startDate(storyboard.getStartDate())
                    .landmarkInfo(landmarkInfo)
                    .build());
        }

        return storyboardSummaryDTOList;
    }

    public List<SceneSummaryDTO> getStoryboardDetail(Long id) {
        Storyboard storyboard = storyboardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Storyboard not found"));


        List<Scene> sceneList = storyboard.getScenes();

        List<SceneSummaryDTO> sceneSummaryDTOList = new ArrayList<>();

        for (Scene scene : sceneList) {
            sceneSummaryDTOList.add(SceneSummaryDTO.builder()
                    .sceneId(scene.getId())
                    .orderNum(scene.getOrderNum())
                    .title(scene.getTitle())
                    .description(scene.getDescription())
                    .build());
        }

        return sceneSummaryDTOList;
    }

    public void updateStoryboard(Long id, StoryboardUpdateDTO request) {
        // 현재 로그인한 사용자 정보 조회
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Storyboard storyboard = storyboardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Storyboard not found"));

        if (!storyboard.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("You are not the owner of this storyboard");
        }

        storyboard.setTitle(request.getTitle());
        storyboard.setStartDate(request.getStartDateTime());
        storyboard.setEndDate(request.getEndDateTime());

        storyboardRepository.save(storyboard);

    }

    public void deleteStoryboard(Long id) {
        // 현재 로그인한 사용자 정보 조회
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Storyboard storyboard = storyboardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Storyboard not found"));

        if (!storyboard.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("You are not the owner of this storyboard");
        }

        storyboardRepository.delete(storyboard);
        storyboardRepository.flush(); // delete 즉시 반영
    }

    /***
     * 예시 스토리보드 목록 조회
     * @return
     */
    public List<StoryboardSummaryDTO> getExampleStoryboards() {
        List<Storyboard> storyboardExamples = storyboardRepository.findByIsExampleTrue();

        List<StoryboardSummaryDTO> storyboardSummaryDTOList = new ArrayList<>();

        // StoryboardSummaryDTO로 변환
        for (Storyboard storyboard : storyboardExamples) {
            Landmark landmark = storyboard.getLandmark();
            String province = landmark.getRegion().getProvince();
            String district = landmark.getRegion().getDistrict();
            String name = landmark.getName();

            // province, district, name을 합쳐서 landmarkInfo 생성
            String landmarkInfo = String.format("%s, %s, %s", province, district, name);

            storyboardSummaryDTOList.add(StoryboardSummaryDTO.builder()
                    .id(storyboard.getId())
                    .title(storyboard.getTitle())
                    .startDate(storyboard.getStartDate())
                    .companions(storyboard.getCompanions())
                    .landmarkInfo(landmarkInfo)
                    .build());
        }

        return storyboardSummaryDTOList;

    }


    /***
     * 예시 스토리보드 상세 조회
     * @param id
     * @return
     */
    public List<SceneSummaryDTO> getExampleStoryboardDetail(Long id) {
        Storyboard storyboard = storyboardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ExampleStoryboard not found"));


        List<Scene> sceneList = storyboard.getScenes();

        List<SceneSummaryDTO> sceneSummaryDTOList = new ArrayList<>();

        for (Scene scene : sceneList) {
            sceneSummaryDTOList.add(SceneSummaryDTO.builder()
                    .sceneId(scene.getId())
                    .orderNum(scene.getOrderNum())
                    .title(scene.getTitle())
                    .description(scene.getDescription())
                    .build());
        }

        return sceneSummaryDTOList;

    }
}
