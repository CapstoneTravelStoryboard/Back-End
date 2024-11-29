package com.example.travel_sculptor.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    private String destination;

    private String companions;

    @Column(columnDefinition = "TEXT")
    private String purpose;

    @Column(columnDefinition = "TEXT")
    private String intro;

    @Column(columnDefinition = "TEXT")
    private String outro;

    @JsonManagedReference
    @OneToMany(mappedBy = "storyboard", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderNum ASC")
    private List<Scene> scenes = new ArrayList<>();

    @Builder
    public Storyboard(String title, String destination, String companions,
                      String purpose, String intro, String outro) {
        this.title = title;
        this.destination = destination;
        this.companions = companions;
        this.purpose = purpose;
        this.intro = intro;
        this.outro = outro;
    }

    protected void setMember(Member member) {
        this.member = member;
    }

    // 연관관계 편의 메서드
    public void addScene(Scene scene) {
        this.scenes.add(scene);
        scene.setStoryboard(this);
    }
}