package com.lecturefeed.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@Configuration
@Order(1)
public class AdminSecurityConfigAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and()
            .authorizeRequests()
            .antMatchers("/auth/admin", "/session/create")
            .access("hasIpAddress('127.0.0.1') or hasIpAddress('::1')")
            .anyRequest().authenticated();
    }
}

