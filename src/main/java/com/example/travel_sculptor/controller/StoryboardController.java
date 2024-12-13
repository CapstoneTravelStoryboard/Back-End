package com.example.travel_sculptor.controller;

import com.example.travel_sculptor.dto.scene.SceneSummaryDTO;
import com.example.travel_sculptor.dto.storyboard.StoryboardCreateRequestDTO;
import com.example.travel_sculptor.dto.storyboard.StoryboardCreateResponseDTO;
import com.example.travel_sculptor.dto.storyboard.StoryboardSummaryDTO;
import com.example.travel_sculptor.dto.storyboard.StoryboardUpdateDTO;
import com.example.travel_sculptor.service.FastapiService;
import com.example.travel_sculptor.service.MemberService;
import com.example.travel_sculptor.service.StoryboardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/{tripId}/storyboards")
@RequiredArgsConstructor
public class StoryboardController {

    private final StoryboardService storyboardService;
    private final FastapiService fastapiService;
    private final MemberService memberService;


    /***
     * 스토리보드를 생성
     * @return 장면 리스트
     */
    @Operation(summary = "스토리보드 생성", description = "생성된 스토리보드 id만 던져주는게 나을지? " +
            "id와 함께 해당 스토리보드의 장면 리스트 정보도 같이 던져주는게 나을지?" +
            "생성된 id만 반환받는거면, 다음페이지 구성할 땐, '스토리보드 상세조회'를 하는 식으로 해야함.")
    @PostMapping
    public StoryboardCreateResponseDTO create(@PathVariable Long tripId, @RequestBody StoryboardCreateRequestDTO storyboardCreateRequestDTO) {
        return fastapiService.createStoryboard(tripId, storyboardCreateRequestDTO);
    }

    /***
     * 스토리보드 장면 별 이미지 생성
     */
    @Operation(summary = "이미지 생성", description = "스토리보드 장면 별 이미지 생성. 응답 데이터는 없다. " +
            "'이미지 생성 요청 확인됐습니다'란 문자열이 바로 반환된다. 백그라운드에서 이미지 S3에 자동 업로드 될 것.")
    @PostMapping("/{id}/images")
    public ResponseEntity<String> createImages(@PathVariable Long id, @RequestBody String season) throws Exception {
        fastapiService.createImages(id, season);
        return ResponseEntity.status(200).body("이미지 생성 요청 확인되었습니다.");
    }


    /***
     * a single trip can have multiple storyboards
     * @return storyboard 요약 정보를 담은 DTO 객체 list (제목, 여행날짜, 위치)
     */
    @Operation(summary = "스토리보드 조회", description = "여행별 스토리보드 조회")
    @GetMapping
    public List<StoryboardSummaryDTO> getStoryboards(@PathVariable Long tripId) {
        return storyboardService.getStoryboards(tripId);
    }


    /***
     * 스토리보드 하나를 누르면, scene list를 볼 수 있다.
     * @return
     */
    @Operation(summary = "스토리보드 상세 조회", description = "장면 리스트들을 볼 수 있다. 응답 body중 id는 Scene의 id이다. ")
    @GetMapping("/{id}")
    public List<SceneSummaryDTO> getStoryboard(@PathVariable Long id) {
        return storyboardService.getStoryboardDetail(id);
    }

    @Operation(summary = "스토리보드 수정", description = "Post 요청이며, 입력한 정보 수정. 수정 폼에서 사용자가 바꾸지 않은 값들이 있어도, 그냥 다 넣어서 보내면 된다.")
    @PostMapping("/{id}")
    public ResponseEntity<String> updateStoryboard(@PathVariable Long id, @RequestBody StoryboardUpdateDTO request) {
        storyboardService.updateStoryboard(id, request);
        return ResponseEntity.ok("Storyboard updated successfully");
    }

    @Operation(summary = "스토리보드 삭제", description = "스토리보드 삭제")
    @DeleteMapping("/{id}")
    public String deleteStoryboard(@PathVariable Long id) {
        storyboardService.deleteStoryboard(id);
        return "deleteStoryboard";
    }

//    /***
//     * 스토리보드를 다시 로드한다.
//     * @return scene list
//     */
//    @PostMapping("/{id}/reload")
//    public String reloadStoryboard() {
//        return "reloadStoryboard";
//    }
}
