package com.example.SecurityProject.config;

import com.example.SecurityProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// @EnableWebSecurity: spring security 설정임을 인식
@EnableWebSecurity
// final 생성자주입
@RequiredArgsConstructor
public class SecurityConfig{

    // final 은 @Autowired 필드주입 불가능.
    private final UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // csrf, cors는 사용 X
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        httpRequests -> httpRequests
                                // /api/v1~~ 은 모든 접근을 허용.
                                .requestMatchers("/api/v1/users/login").permitAll()
                                // 나머지 리소스(anyRequest) 접근에 대해서는 인증(authenticated)을 거쳐야 함.
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
