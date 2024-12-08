package com.example.travel_sculptor.repository;

import com.example.travel_sculptor.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    // 특정 district와 province로 id 가져오기
    Optional<Region> findByProvinceAndDistrict(String province, String district);
}
