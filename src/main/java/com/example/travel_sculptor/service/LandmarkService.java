package com.example.travel_sculptor.service;

import com.example.travel_sculptor.domain.Landmark;
import com.example.travel_sculptor.domain.Region;
import com.example.travel_sculptor.dto.landmark.LandmarkDTO;
import com.example.travel_sculptor.repository.LandmarkRepository;
import com.example.travel_sculptor.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LandmarkService {

    private final LandmarkRepository landmarkRepository;
    private final RegionRepository regionRepository;

    /***
     * 지역별 여행지(Landmark) 조회
     * @param province
     * @param district
     * @return
     */
    public List<LandmarkDTO> getLandmarksByDistrictAndProvince(String province, String district) {
        Optional<Region> region = regionRepository.findByProvinceAndDistrict(province, district);

        if (region.isEmpty()) {
            throw new IllegalArgumentException("Region not found");
        }

        return landmarkRepository.findAllByRegionId(region.get().getId())
                .stream()
                .map(landmark -> new LandmarkDTO(landmark.getId(), landmark.getName()))
                .collect(Collectors.toList());
    }


    /***
     * 여행지(Landmark) 별 테마 조회
     * 하나의 String 조회해서 paring하여, List<String>으로 반환
     * @return
     */
    public List<String> getThemes(Long landmarkId) {
        // landmarkId로 Landmark의 hashtag 조회
        // hashtag는 "#관광지, #숙박, #평창, #평창숙소" 식으로 하나의 문자열로 저장되어있음
        Optional<Landmark> landmark = landmarkRepository.findById(landmarkId);

        if (landmark.isEmpty()) {
            throw new IllegalArgumentException("Landmark not found");
        }

        String hashtag = landmark.get().getHashTag();

        // ','로 나눈 뒤 앞의 '#' 제거하고 List<String>으로 반환
        return Arrays.stream(hashtag.split(","))
                .map(tag -> tag.trim().replaceFirst("#", "")) // 각 태그에서 '#' 제거
                .toList();
    }

}
