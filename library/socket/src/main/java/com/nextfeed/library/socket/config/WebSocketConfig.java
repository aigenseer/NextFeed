package com.nextfeed.library.socket.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final CustomHandshakeHandler customHandshakeHandler;

    @Value("${nextfeed.socket.endpoint.prefix}")
    private String endpointPrefix;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("Connect to endpoint %s/ws".formatted(endpointPrefix));
        registry
                .addEndpoint("%s/ws".formatted(endpointPrefix))
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(customHandshakeHandler)
                .withSockJS();
    }


}
