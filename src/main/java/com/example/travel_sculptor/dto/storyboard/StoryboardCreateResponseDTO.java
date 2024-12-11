package com.example.travel_sculptor.dto.storyboard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class StoryboardCreateResponseDTO {

    private Long id; // 생성된 스토리보드 id
    private List<SceneSummaryDTO> storyboardScenes = new ArrayList<>();

    @Builder
    public StoryboardCreateResponseDTO(Long id, List<SceneSummaryDTO> storyboardScenes) {
        this.id = id;
        this.storyboardScenes = storyboardScenes;
    }

    @Getter
    @Setter
    public static class SceneSummaryDTO {
        private int orderNum;
        private String sceneTitle;
        private String description;
    }
}
