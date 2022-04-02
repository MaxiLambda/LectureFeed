package com.lecturefeed.socket;

import com.lecturefeed.manager.ParticipantManager;
import com.lecturefeed.socket.controller.core.CustomHandshakeHandler;
import com.lecturefeed.socket.controller.core.WebSocketHolderService;
import com.lecturefeed.socket.controller.core.event.WebSocketSessionEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.net.InetSocketAddress;
import java.security.Principal;


@AllArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final CustomHandshakeHandler customHandshakeHandler;
    private final WebSocketSessionEventPublisher webSocketSessionEventPublisher;


    @Override
    public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(final WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
                    @Override
                    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                        webSocketSessionEventPublisher.onWebSocketSessionCreated(session);
                        super.afterConnectionEstablished(session);
                    }
                };
            }
        });
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/admin", "/participant", "/session");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .setAllowedOriginPatterns("http://localhost:4200")
                .setHandshakeHandler(customHandshakeHandler)
                .withSockJS();
    }



}