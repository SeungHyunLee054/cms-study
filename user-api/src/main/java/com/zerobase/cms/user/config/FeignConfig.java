package com.zerobase.cms.user.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Qualifier(value = "mailgun")
    @Bean
    public BasicAuthRequestInterceptor basicAuthenticationInterceptor() {
        return new BasicAuthRequestInterceptor("api", "e0a2365d2b305ec876c829819857b913-28e9457d-db05fd50");
    }
}
