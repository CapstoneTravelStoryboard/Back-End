package com.example.travel_sculptor.dto.scene;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SceneSummaryDTO {
    @Schema(description = "Scene id")
    private Long id;
    private Integer orderNum;
    private String title;
    private String description;

    @Builder
    public SceneSummaryDTO(Long id, Integer orderNum, String title, String description) {
        this.id = id;
        this.orderNum = orderNum;
        this.title = title;
        this.description = description;
    }
}
