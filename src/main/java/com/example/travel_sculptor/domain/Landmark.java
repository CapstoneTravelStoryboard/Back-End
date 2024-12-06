package com.example.travel_sculptor.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Landmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(nullable = false)
    private String name;      // 장소명

    // TEXT 타입으로 변경
    @Column(columnDefinition = "TEXT")
    private String description;  // 설명

    private String hashTag;     // 해시태그
}
