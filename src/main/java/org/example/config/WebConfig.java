package org.example.config;

import org.example.filter.HmacAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<HmacAuthFilter> hmacAuthFilter() {
        FilterRegistrationBean<HmacAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HmacAuthFilter());
        registrationBean.addUrlPatterns("/api/*"); // Apply to desired endpoints
        return registrationBean;
    }
}
