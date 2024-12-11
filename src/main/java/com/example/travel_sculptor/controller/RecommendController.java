package com.example.travel_sculptor.controller;

import com.example.travel_sculptor.dto.fastapi.IotroFastapiResponseDTO;
import com.example.travel_sculptor.dto.recommend.TitleRequestDTO;
import com.example.travel_sculptor.service.FastapiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendController {

    private final FastapiService fastapiService;


    /***
     * 스토리보드 제목 추천
     */
    @PostMapping("/titles")
    public List<String> recommendTitle(@RequestBody TitleRequestDTO titleRequestDTO) {
        return fastapiService.recommendTitle(titleRequestDTO);
    }

    /***
     * 스토리보드 인트로 & 아웃트로 추천
     */
    @PostMapping("/iotros")
    public IotroFastapiResponseDTO recommendIntroOutro(@RequestBody String title) {
        return fastapiService.recommendIntroOutro(title);
    }
}
