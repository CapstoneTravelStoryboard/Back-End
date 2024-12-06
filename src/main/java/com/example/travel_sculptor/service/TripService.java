package com.example.travel_sculptor.service;

import com.example.travel_sculptor.domain.Trip;
import com.example.travel_sculptor.repository.TripRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public Long createTrip(String title) {
        // trip entity create
        Trip trip = Trip.builder()
                .title(title)
                .build();

        return tripRepository.save(trip).getId();
    }
}
