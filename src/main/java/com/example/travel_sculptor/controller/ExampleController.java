package com.example.travel_sculptor.controller;

import com.example.travel_sculptor.dto.scene.SceneSummaryDTO;
import com.example.travel_sculptor.dto.storyboard.StoryboardSummaryDTO;
import com.example.travel_sculptor.service.StoryboardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/examples")
@RequiredArgsConstructor
public class ExampleController {

    private final StoryboardService storyboardService;

    /**
     * 예시 스토리보드 목록을 조회합니다.
     *
     * @return 예시 스토리보드 요약 정보를 담은 DTO 객체 list (제목, 여행날짜, 위치)
     */
    @Operation(summary = "예시 스토리보드 목록 조회",
            description = "로그인하지 않은 사용자도 볼 수 있는 예시 스토리보드 목록")
    @GetMapping
    public List<StoryboardSummaryDTO> getExampleStoryboards() {
        return storyboardService.getExampleStoryboards();
    }

    /**
     * 특정 예시 스토리보드의 상세 정보를 조회합니다.
     *
     * @param id 예시 스토리보드 id
     * @return 해당 스토리보드의 장면 리스트
     */
    @Operation(summary = "예시 스토리보드 상세 조회",
            description = "특정 예시 스토리보드의 장면 리스트들을 볼 수 있다. 응답 body중 id는 Scene의 id이다.")
    @GetMapping("/{id}")
    public List<SceneSummaryDTO> getExampleStoryboardDetail(@PathVariable Long id) {
        return storyboardService.getExampleStoryboardDetail(id);
    }
}