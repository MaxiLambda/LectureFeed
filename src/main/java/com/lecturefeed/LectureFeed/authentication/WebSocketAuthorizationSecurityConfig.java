package com.lecturefeed.LectureFeed.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketAuthorizationSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    @Override
    protected void configureInbound(final MessageSecurityMetadataSourceRegistry messages) {
        messages.simpDestMatchers("/admin/**").hasRole("ADMIN");
        messages.simpDestMatchers("/participant/**").hasRole("PARTICIPANT");
    }

    protected boolean sameOriginDisabled() {
        return true;
    }
}
