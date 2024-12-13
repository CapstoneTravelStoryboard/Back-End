package com.example.travel_sculptor.dto.recommend;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TitleRequestDTO {
    /***
     * destination, purpose, companion, companion_count, season, description
     *
     * prompt = f"여행지: {destination}, 여행지 특성: {description}, 여행 목적: {purpose}, 여행지 계절: {season}, 동행인: {companion} ({companion_count}명)\n"
     * prompt += "위 정보에 기반하여 여행 영상의 제목을 5가지 추천해줘."
     */
    private Long landmarkId;
    private String purpose; //여행목적 (hashtag 값)
    private String companions; //동행인
    private int companionCount; //동행인 수
    private String season;


    @Builder
    public TitleRequestDTO(Long landmarkId, String purpose, String companions, int companionCount, String season) {
        this.landmarkId = landmarkId;
        this.purpose = purpose;
        this.companions = companions;
        this.companionCount = companionCount;
        this.season = season;
    }
}
