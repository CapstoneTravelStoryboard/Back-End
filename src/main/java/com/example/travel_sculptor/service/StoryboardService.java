package com.example.travel_sculptor.service;

import com.example.travel_sculptor.domain.Landmark;
import com.example.travel_sculptor.domain.Scene;
import com.example.travel_sculptor.domain.Storyboard;
import com.example.travel_sculptor.domain.Trip;
import com.example.travel_sculptor.dto.scene.SceneSummaryDTO;
import com.example.travel_sculptor.dto.storyboard.StoryboardSummaryDTO;
import com.example.travel_sculptor.repository.StoryboardRepository;
import com.example.travel_sculptor.repository.TripRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryboardService {

    private final StoryboardRepository storyboardRepository;
    private final TripRepository tripRepository;

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
            String landmarkInfo = String.format("%s, %s, %s", province, district, name);

            storyboardSummaryDTOList.add(StoryboardSummaryDTO.builder()
                    .id(storyboard.getId())
                    .title(storyboard.getTitle())
                    .startDate(storyboard.getStartDate())
                    .landmarkInfo(landmarkInfo)
                    .build());
        }

        return storyboardSummaryDTOList;
    }

    public SceneSummaryDTO getStoryboardDetail(Long id) {
        Storyboard storyboard = storyboardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Storyboard not found"));


        List<Scene> sceneList = storyboard.getScenes();
    }
}
