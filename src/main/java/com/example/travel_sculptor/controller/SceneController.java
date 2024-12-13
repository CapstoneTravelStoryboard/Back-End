package com.example.travel_sculptor.controller;

import com.example.travel_sculptor.dto.scene.SceneDetailDTO;
import com.example.travel_sculptor.service.SceneService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/storyboards/{storyboardId}/scenes")
@RequiredArgsConstructor
public class SceneController {

    private final SceneService sceneService;

    @Operation(summary = "장면 상세 조회", description = "스토리보드의 장면 상세를 조회합니다.")
    @GetMapping("/{id}")
    public SceneDetailDTO getSceneDetail(@PathVariable Long id) {
        return sceneService.getSceneDetail(id);
    }

    @Operation(summary = "장면 수정", description = "스토리보드 생성 시에도 호출되는 API입니다. 수정 폼에서 바뀔 수 있는 정보만 전달합니다." +
            "사용자가 수정했는지 안 했는지는 체크할 필요 없음. 예를 들어, '바뀔 수 있는 정보'가 description, cameraAngle, cameraMovement, composition이라면, " +
            "이 네 가지 정보만 전달하면 됩니다.")
    @PostMapping("/{id}")
    public ResponseEntity<String> updateScene(@PathVariable Long id, @RequestBody SceneDetailDTO sceneDetailDTO) {
        sceneService.updateScene(id, sceneDetailDTO);
        return ResponseEntity.ok("Scene updated");
    }

    @Operation(summary = "장면 삭제", description = "스토리보드의 장면을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteScene(@PathVariable Long id) {
        sceneService.deleteScene(id);
        return ResponseEntity.ok("Scene deleted");
    }
}
