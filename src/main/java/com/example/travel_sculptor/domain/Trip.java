package com.example.travel_sculptor.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "trip")
public class Trip extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Column(name = "day_start")
    private LocalDateTime dayStart;

    @JsonManagedReference
    @OneToMany(mappedBy = "trip")
    private List<Storyboard> storyboards = new ArrayList<>();

    @Builder
    public Trip(Member member, String title, LocalDateTime dayStart) {
        this.member = member;
        this.title = title;
        this.dayStart = dayStart;
    }

    // update dayStart when Storyboard is created
    public void updateDayStart(LocalDateTime dayStart) {
        this.dayStart = dayStart;
    }


}
