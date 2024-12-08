package com.example.travel_sculptor.service;

import com.example.travel_sculptor.domain.Landmark;
import com.example.travel_sculptor.repository.LandmarkRepository;
import com.example.travel_sculptor.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LandmarkServiceTest {

    private final LandmarkRepository landmarkRepository = Mockito.mock(LandmarkRepository.class);
    private final RegionRepository regionRepository = Mockito.mock(RegionRepository.class);
    private final LandmarkService landmarkService = new LandmarkService(landmarkRepository, regionRepository);

    @Test
    void getThemes_shouldReturnParsedThemes() {
        // Given
        Long landmarkId = 1L;
        String hashtags = "#관광지, #숙박, #평창";
        Landmark landmark = new Landmark();
        landmark.setHashTag(hashtags);

        when(landmarkRepository.findById(landmarkId)).thenReturn(Optional.of(landmark));

        // When
        List<String> themes = landmarkService.getThemes(landmarkId);

        // Then
        assertEquals(List.of("#관광지", " #숙박", " #평창", " #평창숙소"), themes);
        verify(landmarkRepository, times(1)).findById(landmarkId);
    }
}