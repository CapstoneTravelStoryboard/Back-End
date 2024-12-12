package com.example.travel_sculptor.dto.storyboard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryboardSummaryDTO {
    private Long id;
    private String title;
    private LocalDateTime startDate;
    private String landmarkInfo;

    @Builder
    public StoryboardSummaryDTO(Long id, String title, LocalDateTime startDate, String landmarkInfo) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.landmarkInfo = landmarkInfo;
    }
}
