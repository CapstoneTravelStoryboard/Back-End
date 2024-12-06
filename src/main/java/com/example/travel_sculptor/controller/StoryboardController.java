package com.example.travel_sculptor.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/storyboards")
public class StoryboardController {

    /***
     * a single trip can have multiple storyboards
     * @return storyboard 요약 정보를 담은 DTO 객체 list (제목, 여행날짜, 위치)
     */
    @GetMapping
    public String getStoryboards() {
        return "getStoryboards";
    }

    /***
     * 스토리보드를 생성
     * @return 장면 리스트
     */
    @PostMapping
    public String createStoryboard() {
        return "createStoryboard";
    }

    /***
     * 스토리보드 하나를 누르면, scene list를 볼 수 있다.
     * @return
     */
    @GetMapping("/{id}")
    public String getStoryboard() {
        return "getStoryboard";
    }

    @PatchMapping("/{id}")
    public String updateStoryboard() {
        return "updateStoryboard";
    }

    @DeleteMapping("/{id}")
    public String deleteStoryboard() {
        return "deleteStoryboard";
    }

    /***
     * 스토리보드를 다시 로드한다.
     * @return scene list
     */
    @PostMapping("/{id}/reload")
    public String reloadStoryboard() {
        return "reloadStoryboard";
    }
}
