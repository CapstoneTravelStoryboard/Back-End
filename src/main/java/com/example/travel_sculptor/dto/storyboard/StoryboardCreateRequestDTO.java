package com.example.travel_sculptor.dto.storyboard;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryboardCreateRequestDTO {
    private Long tripId;    //여행ID
    private Long landmarkId; // -> 여행지명(destination), 여행지 특성(description) 조회
    private String purpose;  //여행목적
    private String companions;  //동행인
    private Integer companionCount;  //동행인 수
    private String season;  //계절
    private String title;   //제목
    private String intro;   //인트로
    private String outro;   //아웃트로
    private LocalDateTime startDate; //여행시작일
    private LocalDateTime endDate;   //여행종료일
}
