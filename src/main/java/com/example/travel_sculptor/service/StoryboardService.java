package com.example.travel_sculptor.service;

import com.example.travel_sculptor.repository.StoryboardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryboardService {

    private final StoryboardRepository storyboardRepository;

}
