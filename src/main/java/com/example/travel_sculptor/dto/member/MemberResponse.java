package com.example.travel_sculptor.dto.member;

import com.example.travel_sculptor.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String name;
    private String email;


    // Member 엔티티를 MemberResponse로 변환하는 정적 메서드
    public static MemberResponse of(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail()
        );
    }
}
