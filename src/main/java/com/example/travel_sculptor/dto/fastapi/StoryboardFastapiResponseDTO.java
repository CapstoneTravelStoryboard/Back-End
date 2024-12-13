package com.example.travel_sculptor.dto.fastapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class StoryboardFastapiResponseDTO {

    @JsonProperty("storyboard_scenes")
    List<SceneDTO> storyboardScenes = new ArrayList<>();

    @Getter
    @Setter
    public static class SceneDTO {
        @JsonProperty("order_num")
        private int orderNum;
        @JsonProperty("scene_title")
        private String sceneTitle;
        private String description;
        @JsonProperty("camera_angle")
        private String cameraAngle;
        @JsonProperty("camera_movement")
        private String cameraMovement;
        private String composition;
    }
}
