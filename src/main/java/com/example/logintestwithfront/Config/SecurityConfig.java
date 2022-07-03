package com.example.logintestwithfront.Config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // 기본적인 Web 보안을 활성화함.
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 추가적인 웹 설정을 위해 상속

    @Override // WebSecurityConfigurerAdapter 내부 configure 함수를 오버라이드
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf 해제
                .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근 제한 설정
                .antMatchers("/oauth/token", "/api/**").permitAll() // "/oauth/token"에 대한 접근 권한은 인증 없이 접근 허용 -> CORS 문제 해결
                .anyRequest().authenticated(); // 나머지 요청에 대해서는 인증을 받아야 한다.
    }
}


