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

    @Column(nullable = false)
    private String title;

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

    @Builder
    public Scene(String title, Integer orderNum, String description, String cameraAngle, String cameraMovement, String composition) {
        this.title = title;
        this.orderNum = orderNum;
        this.description = description;
        this.cameraAngle = cameraAngle;
        this.cameraMovement = cameraMovement;
        this.composition = composition;
    }

    protected void setStoryboard(Storyboard storyboard) {
        this.storyboard = storyboard;
    }

    public void updateScene(Integer orderNum, String title, String description, String cameraAngle, String cameraMovement, String composition) {
        this.orderNum = orderNum;
        this.title = title;
        this.description = description;
        this.cameraAngle = cameraAngle;
        this.cameraMovement = cameraMovement;
        this.composition = composition;
    }
}
