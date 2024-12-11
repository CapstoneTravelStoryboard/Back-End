package com.example.travel_sculptor.repository;

import com.example.travel_sculptor.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    // 특정 member의 trip 가져오기
    List<Trip> findAllByMemberId(Long memberId);
}
