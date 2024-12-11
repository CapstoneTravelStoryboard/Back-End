package com.example.travel_sculptor.controller;

import com.example.travel_sculptor.dto.storyboard.StoryboardCreateRequestDTO;
import com.example.travel_sculptor.dto.storyboard.StoryboardCreateResponseDTO;
import com.example.travel_sculptor.service.FastapiService;
import com.example.travel_sculptor.service.MemberService;
import com.example.travel_sculptor.service.StoryboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/storyboards")
@RequiredArgsConstructor
public class StoryboardController {

    private final StoryboardService storyboardService;
    private final FastapiService fastapiService;
    private final MemberService memberService;


    /***
     * 스토리보드를 생성
     * @return 장면 리스트
     */
    @PostMapping
    public StoryboardCreateResponseDTO create(@RequestBody StoryboardCreateRequestDTO storyboardCreateRequestDTO) {

        return fastapiService.createStoryboard(storyboardCreateRequestDTO);
    }

    /***
     * 스토리보드 장면 별 이미지 생성
     */
    @PostMapping("/{id}/images")
    public ResponseEntity<String> createImages(@PathVariable Long id, @RequestBody String season) throws Exception {
        fastapiService.createImages(id, season);
        return ResponseEntity.status(200).body("이미지 생성 요청 확인되었습니다.");
    }


    /***
     * a single trip can have multiple storyboards
     * @return storyboard 요약 정보를 담은 DTO 객체 list (제목, 여행날짜, 위치)
     */
    @GetMapping
    public String getStoryboards() {
        return "getStoryboards";
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
