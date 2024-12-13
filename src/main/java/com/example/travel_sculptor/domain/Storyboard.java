package com.example.travel_sculptor.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "storyboards")
public class Storyboard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Setter
    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landmark_id")
    private Landmark landmark;

    private String companions;

    private int companionCount;

    @Column(columnDefinition = "TEXT")
    private String purpose;

    @Setter
    private LocalDateTime startDate;

    @Setter
    private LocalDateTime endDate;

    @Column(columnDefinition = "TEXT")
    private String intro;

    @Column(columnDefinition = "TEXT")
    private String outro;

    // 예시 스토리보드인지 아닌지
    private boolean isExample = false;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @JsonManagedReference
    @OneToMany(mappedBy = "storyboard", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderNum ASC")
    private List<Scene> scenes = new ArrayList<>();

    @Builder
    public Storyboard(Trip trip, Member member, String title, Landmark landmark, String companions, int companionCount, String purpose, LocalDateTime startDate, LocalDateTime endDate, String intro, String outro) {
        this.trip = trip;
        this.member = member;
        this.title = title;
        this.landmark = landmark;
        this.companions = companions;
        this.companionCount = companionCount;
        this.purpose = purpose;
        this.startDate = startDate;
        this.endDate = endDate;
        this.intro = intro;
        this.outro = outro;
    }

    // 연관관계 편의 메서드
    public void addScene(Scene scene) {
        this.scenes.add(scene);
        scene.setStoryboard(this);
    }
}
