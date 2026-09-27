package com.dev_course_assignment1.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("할 일 서비스 API 명세서")
                        .description("할 일(Todo) 서비스 백엔드 API 명세서입니다.")
                        .version("v1.0.0"));
    }
}
