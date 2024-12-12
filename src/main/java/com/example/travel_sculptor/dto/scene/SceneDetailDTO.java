package com.example.travel_sculptor.dto.scene;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SceneDetailDTO {
    private Long id;
    private Integer orderNum;
    private String title;
    private String description;
    private String cameraAngle;
    private String cameraMovement;
    private String composition;

    @Builder
    public SceneDetailDTO(Long id, Integer orderNum, String title, String description, String cameraAngle, String cameraMovement, String composition) {
        this.id = id;
        this.orderNum = orderNum;
        this.title = title;
        this.description = description;
        this.cameraAngle = cameraAngle;
        this.cameraMovement = cameraMovement;
        this.composition = composition;
    }
}
