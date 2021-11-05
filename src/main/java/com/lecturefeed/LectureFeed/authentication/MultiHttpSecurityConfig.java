package com.lecturefeed.LectureFeed.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MultiHttpSecurityConfig {

    @Configuration
    @Order(1)
    public static class AdminSecurityConfigAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .cors().and()
                    .authorizeRequests()
                    .antMatchers("/auth/admin")
                    .access("hasIpAddress(\"127.0.0.1\") or hasIpAddress(\"::1\")");
        }
    }

}