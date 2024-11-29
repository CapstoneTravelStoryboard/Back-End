package com.example.travel_sculptor.config;

import com.example.travel_sculptor.config.auth.CustomOAuth2MemberService;
import com.example.travel_sculptor.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final CustomOAuth2MemberService customOAuth2UserService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                /***
                 * anyRequest().authenticated() : 따로 설정하지 않은 이외 요청에 대해 인증을 요구한다.
                 * Spring Security는 자동으로 google 로그인 페이지로 리다이렉트한다.
                 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/api/v1/examples/**").permitAll()
                        .requestMatchers("/admin").hasRole(Role.ADMIN.name())
                        .requestMatchers("/api/v1/recommendations/**", "/api/v1/storyboards/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                );

        return http.build();
    }
}
