package com.example.travel_sculptor.dto.storyboard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class StoryboardSummaryDTO {
    private Long id;
    private String title;
    private String startDate;
    private String companions;
    private String landmarkInfo;

    @Builder
    public StoryboardSummaryDTO(Long id, String title, LocalDateTime startDate, String companions, String landmarkInfo) {
        this.id = id;
        this.title = title;
        this.startDate = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.companions = companions;
        this.landmarkInfo = landmarkInfo;
    }
}
