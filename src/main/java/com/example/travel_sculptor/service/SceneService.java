package com.example.travel_sculptor.service;

import com.example.travel_sculptor.domain.Member;
import com.example.travel_sculptor.domain.Scene;
import com.example.travel_sculptor.dto.scene.SceneDetailDTO;
import com.example.travel_sculptor.repository.MemberRepository;
import com.example.travel_sculptor.repository.SceneRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SceneService {

    private final SceneRepository sceneRepository;
    private final MemberRepository memberRepository;

    public SceneDetailDTO getSceneDetail(Long id) {
        Optional<Scene> byId = sceneRepository.findById(id);
        if (byId.isEmpty())
            throw new IllegalArgumentException("Scene not found");

        Scene scene = byId.get();

        return SceneDetailDTO.builder()
                .id(scene.getId())
                .orderNum(scene.getOrderNum())
                .title(scene.getTitle())
                .description(scene.getDescription())
                .cameraAngle(scene.getCameraAngle())
                .cameraMovement(scene.getCameraMovement())
                .composition(scene.getComposition())
                .build();
    }

    public void updateScene(Long id, SceneDetailDTO sceneDetailDTO) {
        // 현재 로그인한 사용자 정보 조회
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Scene scene = sceneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scene not found"));

        if (!scene.getStoryboard().getTrip().getMember().getId().equals(member.getId()))
            throw new IllegalArgumentException("You are not the owner of this Scene");


        // update scene
        scene.updateScene(sceneDetailDTO.getOrderNum(), sceneDetailDTO.getTitle(), sceneDetailDTO.getDescription(),
                sceneDetailDTO.getCameraAngle(), sceneDetailDTO.getCameraMovement(), sceneDetailDTO.getComposition());

        sceneRepository.save(scene);
    }

    public void deleteScene(Long id) {
        // 현재 로그인한 사용자 정보 조회
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Scene scene = sceneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scene not found"));

        if (!scene.getStoryboard().getTrip().getMember().getId().equals(member.getId()))
            throw new IllegalArgumentException("You are not the owner of this Scene");

        sceneRepository.delete(scene);
        sceneRepository.flush();
    }
}
