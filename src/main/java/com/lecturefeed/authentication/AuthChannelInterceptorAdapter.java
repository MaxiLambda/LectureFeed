package com.lecturefeed.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.security.Principal;

@AllArgsConstructor
@Component
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {
    private static final String TOKEN_HEADER = "token";
    @Getter
    private final AuthenticatorService authenticatorService;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT == accessor.getCommand()) {
            final String token = accessor.getFirstNativeHeader(TOKEN_HEADER);
            Principal principal = authenticatorService.getAuthenticated(token);
            if(principal == null){
                throw new AuthenticationCredentialsNotFoundException("token was null or empty.");
            }
            accessor.setUser(principal);
        }
        return message;
    }
}
