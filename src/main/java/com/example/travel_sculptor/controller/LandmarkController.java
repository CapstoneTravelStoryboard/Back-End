package com.example.travel_sculptor.controller;

import com.example.travel_sculptor.dto.landmark.LandmarkDTO;
import com.example.travel_sculptor.service.LandmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/landmarks")
@RequiredArgsConstructor
public class LandmarkController {

    private final LandmarkService landmarkService;

    /***
     * 지역별 여행지(Landmark) 조회
     * API URL : /api/v1/landmarks?province=서울&district=강남구
     */
    @GetMapping
    public List<LandmarkDTO> getLandmarks(
            @RequestParam String province,
            @RequestParam String district) {
        return landmarkService.getLandmarksByDistrictAndProvince(province, district);
    }

    /***
     * 여행지(Landmark) 별 테마 조회
     * 여행지 별로 "#관광지, #숙박, #평창, #평창숙소" 식으로 하나의 문자열로 저장되어있음 -> hashtag로 구분
     * 파싱해서 List<String>으로 반환
     */
    @GetMapping("/themes")
    public List<String> getThemes(@RequestParam Long landmarkId) {
        return landmarkService.getThemes(landmarkId);
    }

}
