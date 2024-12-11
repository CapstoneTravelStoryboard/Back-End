package com.example.travel_sculptor.controller;

import com.example.travel_sculptor.dto.trip.TripListResponseDTO;
import com.example.travel_sculptor.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    /***
     * 사용자는 trips를 조회할 수 있다.
     * @return 여행 리스트
     */
    @GetMapping
    public List<TripListResponseDTO> getTrips() {
        return tripService.getTrips();
    }

    /***
     * 여행 생성
     * @param title
     * @return 여행 id
     */
    @PostMapping
    public ResponseEntity<Long> createTrip(@RequestBody String title) {
        Long tripId = tripService.createTrip(title);
        return ResponseEntity.ok(tripId);
    }

}
