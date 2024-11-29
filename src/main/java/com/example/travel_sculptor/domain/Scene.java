package com.example.travel_sculptor.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "scenes")
@Entity
public class Scene extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storyboard_id", nullable = false)
    private Storyboard storyboard;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SceneType sceneType;

    @Column(nullable = false)
    private Integer orderNum;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String cameraAngle;

    @Column(length = 100)
    private String cameraMovement;

    @Column(length = 100)
    private String composition;

    private String imageUrl;

    @Builder
    public Scene(SceneType sceneType, Integer orderNum, String description,
                 String cameraAngle, String cameraMovement, String composition,
                 String imageUrl) {
        this.sceneType = sceneType;
        this.orderNum = orderNum;
        this.description = description;
        this.cameraAngle = cameraAngle;
        this.cameraMovement = cameraMovement;
        this.composition = composition;
        this.imageUrl = imageUrl;
    }

    protected void setStoryboard(Storyboard storyboard) {
        this.storyboard = storyboard;
    }
}
