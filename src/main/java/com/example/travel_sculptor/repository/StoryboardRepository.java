package com.example.travel_sculptor.repository;

import com.example.travel_sculptor.domain.Storyboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryboardRepository extends JpaRepository<Storyboard, Long> {
    List<Storyboard> findByIsExampleTrue();
}
