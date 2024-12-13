package com.example.travel_sculptor.dto.landmark;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LandmarkDTO {
    // Landmark id : 나중에 해시태그 검색을 위해 필요
    private Long id;
    private String name;


    public LandmarkDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
}
