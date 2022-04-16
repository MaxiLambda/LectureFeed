package com.lecturefeed.authentication;

import com.lecturefeed.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class AdminSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    private final AuthenticatorService authenticatorService;
    private final InetAddressSecurityService inetAddressSecurityService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        PreAuthTokenHeaderFilter preAuthTokenHeaderFilter = new PreAuthTokenHeaderFilter(authenticatorService, inetAddressSecurityService);
        preAuthTokenHeaderFilter.setAuthenticationManager(authentication -> {
            if(authentication.getPrincipal() == null){
                authentication.setAuthenticated(authentication.getPrincipal() != null);
                return authentication;
            }
            return (Authentication) authentication.getPrincipal();
        });

        http
                .antMatcher("/api/**")
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/admin")
                .access("hasIpAddress('127.0.0.1') or hasIpAddress('::1')")
                .antMatchers("/api/auth/participant").permitAll()
                .antMatchers("/api/admin/**", "/api/presenter/**", "/api/session/presenter/**", "/api/environment/**").hasRole(UserRole.ADMINISTRATOR.getRole())
                .antMatchers("/api/session/**").hasAnyRole(UserRole.ADMINISTRATOR.getRole(), UserRole.PARTICIPANT.getRole())
//                .antMatchers("/api/ws/**").authenticated()
                .anyRequest().authenticated()
                .and().addFilter(preAuthTokenHeaderFilter)
        ;

    }

}
