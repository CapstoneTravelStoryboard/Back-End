package com.example.travel_sculptor.controller;

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
     * 지역별 랜드마크 조회
     * API URL : /api/v1/landmarks?province=서울&district=강남구
     */
    @GetMapping
    public List<String> getLandmarksByDistrictAndProvince(
            @RequestParam String province,
            @RequestParam String district) {
        return landmarkService.getLandmarksByDistrictAndProvince(province, district);
    }
}
