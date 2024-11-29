package com.example.travel_sculptor.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "members")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    // provider가 null이면 일반 로그인, null이 아니면 소셜 로그인 (kakao, google, ...)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    // 소셜 로그인 시 provider의 id
    @Column(nullable = false)
    private String providerId;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private String profileImage;    // 프로필 이미지 URL (Optional)

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UserCredential> credentials = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Storyboard> storyboards = new ArrayList<>();


    @Builder
    public Member(String email, String name, Provider provider, String providerId, String profileImage) {
        this.email = email;
        this.name = name;
        this.provider = provider;
        this.providerId = providerId;
        this.profileImage = profileImage;
    }

    // 프로필 이미지 업데이트를 위한 메서드
    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    // update 함수 구현
    public Member update(String name, String picture) {
        this.name = name;
        this.profileImage = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
