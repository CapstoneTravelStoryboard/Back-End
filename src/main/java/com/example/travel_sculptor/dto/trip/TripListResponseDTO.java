package com.example.travel_sculptor.dto.trip;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TripListResponseDTO {
    private Long id;
    private String title;
    private String dayStart;

    @Builder
    public TripListResponseDTO(Long id, String title, String dayStart) {
        this.id = id;
        this.title = title;
        this.dayStart = dayStart;
    }

}
