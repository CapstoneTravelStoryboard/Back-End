package com.example.travel_sculptor.repository;


import com.example.travel_sculptor.domain.Scene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SceneRepository extends JpaRepository<Scene, Long> {
    List<Scene> findAllByStoryboardId(Long storyboardId);

    void deleteByStoryboardId(Long storyboardId);
}
