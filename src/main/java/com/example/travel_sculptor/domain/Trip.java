package com.example.travel_sculptor.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "trip")
public class Trip extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    private String title;

    @Column(name = "day_start")
    private LocalDate dayStart;

    //    @OneToMany(mappedBy = "trip")
//    private List<Storyboard> storyboards = new ArrayList<>();

    @Builder
    // only title
    public Trip(String title) {
        this.title = title;
    }

    // update dayStart when Storyboard is created
    public void updateDayStart(LocalDate dayStart) {
        this.dayStart = dayStart;
    }


}
