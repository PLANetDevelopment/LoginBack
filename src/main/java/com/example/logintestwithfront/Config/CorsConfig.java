package com.example.logintestwithfront.Config;

import com.example.logintestwithfront.JWT.JwtProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버의 json 응답을 자바스크립트가 처리할 수 있도록
        config.addAllowedOrigin("http://localhost:3000"); // 해당 ip의 응답을 허용
        config.addAllowedHeader("*"); // 모든 header에 응답을 허용
        config.addAllowedMethod("*"); // 모든 메소드 요청을 허용

        config.setMaxAge(3600L);
        config.addExposedHeader(JwtProperties.HEADER_STRING);
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }

}