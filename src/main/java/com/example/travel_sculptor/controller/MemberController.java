package com.example.travel_sculptor.controller;

import com.example.travel_sculptor.domain.Member;
import com.example.travel_sculptor.dto.member.MemberLoginRequest;
import com.example.travel_sculptor.dto.member.MemberResponse;
import com.example.travel_sculptor.dto.member.MemberSignupRequest;
import com.example.travel_sculptor.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid MemberSignupRequest request) {
        memberService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@RequestBody @Valid MemberLoginRequest request) {
        Member member = memberService.login(request);
        return ResponseEntity.ok(MemberResponse.of(member));
    }
}
