package com.example.travel_sculptor.service;

import com.example.travel_sculptor.repository.LandmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandmarkService {

    private final LandmarkRepository landmarkRepository;

    public List<String> getLandmarksByDistrictAndProvince(String province, String district) {
        return landmarkRepository.findLandmarkNamesByDistrictAndProvince(province, district);
    }

}
