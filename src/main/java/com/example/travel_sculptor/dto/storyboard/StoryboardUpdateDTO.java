package com.example.travel_sculptor.dto.storyboard;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryboardUpdateDTO {
    private String title;
    private String startDate;
    private String endDate;

    public LocalDateTime getStartDateTime() {
        return LocalDateTime.parse(startDate);
    }

    public LocalDateTime getEndDateTime() {
        return LocalDateTime.parse(endDate);
    }

}
