package com.example.travel_sculptor.dto.scene;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SceneSummaryDTO {
    // Scene의 id
    private Long sceneId;
    private Integer orderNum;
    private String title;
    private String description;

    @Builder
    public SceneSummaryDTO(Long sceneId, Integer orderNum, String title, String description) {
        this.sceneId = sceneId;
        this.orderNum = orderNum;
        this.title = title;
        this.description = description;
    }
}
