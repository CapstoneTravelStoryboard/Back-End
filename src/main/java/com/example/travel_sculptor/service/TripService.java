package com.example.travel_sculptor.service;

import com.example.travel_sculptor.domain.Member;
import com.example.travel_sculptor.domain.Trip;
import com.example.travel_sculptor.dto.trip.TripListResponseDTO;
import com.example.travel_sculptor.repository.MemberRepository;
import com.example.travel_sculptor.repository.TripRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final MemberRepository memberRepository;

    public Long createTrip(String title) {
        // 현재 로그인한 사용자 정보 조회
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));


        // trip entity create
        Trip trip = Trip.builder()
                .member(member)
                .title(title)
                .dayStart(LocalDateTime.now())
                .build();

        return tripRepository.save(trip).getId();
    }

    public List<TripListResponseDTO> getTrips() {
        // 현재 로그인한 사용자 정보 조회
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        List<Trip> trips = tripRepository.findAllByMemberId(member.getId());

        // TripListResponseDTO로 변환
        List<TripListResponseDTO> tripListResponseDTOList = new ArrayList<>();

        for (Trip trip : trips) {
            tripListResponseDTOList.add(TripListResponseDTO.builder()
                    .id(trip.getId())
                    .title(trip.getTitle())
                    .dayStart(trip.getDayStart().toString())
                    .build());
        }

        return tripListResponseDTOList;
    }

    public void deleteTrip(Long tripId) {
        // 현재 로그인한 사용자 정보 조회
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        if (!trip.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("You are not the owner of this trip");
        }

        tripRepository.delete(trip);
        tripRepository.flush(); // delete 즉시 반영
    }

    public void updateTrip(Long tripId, String title) {
        // 현재 로그인한 사용자 정보 조회
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        if (!trip.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("You are not the owner of this trip");
        }

        trip.setTitle(title);

        tripRepository.save(trip);
    }
}
