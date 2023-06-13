package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링 환경설정 파일임을 명시
@EnableWebSecurity //모든 요청 URL은 이 스프링 시큐리티 제어를 받음 , 내부에서 SpringSecurityFilterChain이 동작하여 URL필터가 적용됨
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(). //권한
                    requestMatchers(new AntPathRequestMatcher("/**")).
                    permitAll()
                .and()
                  .csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .and()
                    .headers()
                    .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));

        return http.build();//DefaultSecurityFilterChain 반환
        //대충 코드보면 "/** 에 모든걸 권한을  허용(permitAll) 한다는구나
        //그러니까, 인증하지 않은 사용자도 모든 url요청을 허용하는구나 ㅇㅋㅇㅋ
    }
}
