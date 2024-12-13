package com.example.travel_sculptor.dto.fastapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StoryboardFastapiRequestDTO {
    private String destination;
    private String purpose;
    private String companions;
    @JsonProperty("companion_count")
    private Integer companionCount;
    private String season;
    private String title;
    private String intro;
    private String outro;
    private String description;
    @JsonProperty("image_urls")
    private List<String> imageUrls;


    @Builder

    public StoryboardFastapiRequestDTO(String destination, String purpose, String companions,
                                       Integer companionCount, String season, String title,
                                       String intro, String outro, String description, List<String> imageUrls) {
        this.destination = destination;
        this.purpose = purpose;
        this.companions = companions;
        this.companionCount = companionCount;
        this.season = season;
        this.title = title;
        this.intro = intro;
        this.outro = outro;
        this.description = description;
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        return "StoryboardFastapiRequestDTO{" +
                "destination='" + destination + '\'' +
                ", purpose='" + purpose + '\'' +
                ", companions='" + companions + '\'' +
                ", companionCount=" + companionCount +
                ", season='" + season + '\'' +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", outro='" + outro + '\'' +
                ", description='" + description + '\'' +
                ", imageUrls=" + imageUrls +
                '}';
    }
}
