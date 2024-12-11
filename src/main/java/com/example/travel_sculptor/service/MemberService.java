package com.example.travel_sculptor.service;

import com.example.travel_sculptor.config.auth.JwtTokenProvider;
import com.example.travel_sculptor.domain.Member;
import com.example.travel_sculptor.dto.member.LoginResponse;
import com.example.travel_sculptor.dto.member.MemberLoginRequest;
import com.example.travel_sculptor.dto.member.MemberResponse;
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
    private final JwtTokenProvider jwtTokenProvider;

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
    public LoginResponse login(MemberLoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(member.getEmail(), member.getName());

        return new LoginResponse(token, MemberResponse.of(member));
    }
}
