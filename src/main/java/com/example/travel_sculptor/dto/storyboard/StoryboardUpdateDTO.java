package com.example.travel_sculptor.dto.storyboard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryboardUpdateDTO {
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public StoryboardUpdateDTO(String title, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
