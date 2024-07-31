package com.Likelion12.fit_mate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로(/**)에 대해 CORS 설정을 추가합니다.
        registry.addMapping("/**")
                // 허용할 출처(Origin)를 지정합니다. 여기서는 React 개발 서버의 주소인 http://localhost:3000을 허용합니다.
                .allowedOrigins("http://localhost:3000","http://localhost:8080")
                // 허용할 HTTP 메서드를 지정합니다.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 허용할 HTTP 헤더를 모두 허용합니다.
                .allowedHeaders("*")
                // 자격 증명(쿠키, HTTP 인증 정보)을 요청에서 허용할지 여부를 설정합니다.
                .allowCredentials(true);
    }
}
