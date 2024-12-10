package com.example.travel_sculptor.service;

import com.example.travel_sculptor.domain.Member;
import com.example.travel_sculptor.dto.member.MemberLoginRequest;
import com.example.travel_sculptor.dto.member.MemberSignupRequest;
import com.example.travel_sculptor.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /***
     * 회원가입
     * @param request
     */
    public void signup(MemberSignupRequest request) {
        // 이메일 중복 검사
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 가입된 회원입니다.");
        }

        // 비밀번호 확인 검사
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        memberRepository.save(member);

    }

    /***
     * 로그인
     * @param request
     * @return
     */
    public Member login(MemberLoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("등록되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }
}
