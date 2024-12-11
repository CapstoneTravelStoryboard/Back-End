package com.example.travel_sculptor.dto.fastapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class ImageFastapiRequestDTO {
    @Setter
    @JsonProperty("scene_description")
    String sceneDescription;

    @JsonProperty("storyboard_id")
    private Long storyboardId;

    @JsonProperty("order_num")
    private Integer orderNum;

    private String destination;

    private String purpose;

    private String companion;

    @JsonProperty("companion_count")
    private Integer companionCount;

    private String season;

    @JsonProperty("image_urls")
    private List<String> imageUrls;


    @Builder
    public ImageFastapiRequestDTO(String sceneDescription, Long storyboardId, Integer orderNum, String destination, String purpose, String companion, Integer companionCount, String season, List<String> imageUrls) {
        this.sceneDescription = sceneDescription;
        this.storyboardId = storyboardId;
        this.orderNum = orderNum;
        this.destination = destination;
        this.purpose = purpose;
        this.companion = companion;
        this.companionCount = companionCount;
        this.season = season;
        this.imageUrls = imageUrls;
    }
}
