package com.example.travel_sculptor.config.auth.dto;

import com.example.travel_sculptor.domain.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String name;
    private String email;
    private String profileImage;

    public SessionMember(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.profileImage = member.getProfileImage();
    }
}
