package com.lecturefeed.authentication;

import com.lecturefeed.model.UserRole;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketAuthorizationSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    @Override
    protected void configureInbound(final MessageSecurityMetadataSourceRegistry messages) {
        messages.simpDestMatchers("/admin/**").hasRole(UserRole.ADMINISTRATOR.getRole());
        messages.simpDestMatchers("/participant/**").hasRole(UserRole.PARTICIPANT.getRole());
        messages.simpDestMatchers("/socket/**").hasAnyRole(UserRole.ADMINISTRATOR.getRole(), UserRole.PARTICIPANT.getRole());
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
