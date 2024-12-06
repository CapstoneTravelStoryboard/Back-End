package com.example.travel_sculptor.controller;

import com.example.travel_sculptor.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    /***
     * 여행 생성 API
     */
    @PostMapping
    public ResponseEntity<Long> createTrip(@RequestBody String title) {
        Long tripId = tripService.createTrip(title);
        return ResponseEntity.ok(tripId);
    }

    /***
     * 사용자는 trips를 조회할 수 있다.
     * @return trip 요약 정보를 담은 DTO 객체 list
     */
//    @GetMapping
//    public ResponseEntity<TripListRequestDTO> getTrips() {
//        TripListRequestDTO tripListRequestDTO = tripService.getTrips();
//        return ResponseEntity.ok(tripListRequestDTO);
//    }

    /***
     * 여행을 생성. 제목만 입력받아 기본 정보를 저장.
     */


}
