package com.example.fms.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestIdFilter> requestIdFilter(ObjectMapper objectMapper) {
        FilterRegistrationBean<RequestIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestIdFilter(objectMapper));
        registrationBean.setOrder(1); // set to a low value to ensure early execution
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}
