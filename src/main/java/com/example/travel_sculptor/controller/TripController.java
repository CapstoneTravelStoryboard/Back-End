package com.example.travel_sculptor.controller;

import com.example.travel_sculptor.dto.trip.TripListResponseDTO;
import com.example.travel_sculptor.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "여행 조회")
    @GetMapping
    public List<TripListResponseDTO> getTrips() {
        return tripService.getTrips();
    }

    /***
     * 여행 생성
     * @param title
     * @return 여행 id
     */
    @Operation(summary = "여행 생성", description = "여행 생성하고 trip_id 반환")
    @PostMapping
    public ResponseEntity<Long> createTrip(@RequestBody String title) {
        Long tripId = tripService.createTrip(title);
        return ResponseEntity.ok(tripId);
    }

    /***
     * 여행 수정
     */
    @Operation(summary = "여행 수정", description = "여행 수정")
    @PostMapping("/{tripId}")
    public ResponseEntity<String> updateTrip(@PathVariable Long tripId, @RequestBody String title) {
        tripService.updateTrip(tripId, title);
        return ResponseEntity.ok("Trip updated");
    }

    /***
     * 여행 삭제
     * @param tripId
     * @return
     */
    @Operation(summary = "여행 삭제", description = "여행 삭제")
    @DeleteMapping("/{tripId}")
    public ResponseEntity<String> deleteTrip(@PathVariable Long tripId) {
        tripService.deleteTrip(tripId);
        return ResponseEntity.ok("Trip deleted");
    }

}
