package com.nextfeed.library.security;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 98)
@ConditionalOnProperty({"nextfeed.metrics.enabled"})
public class WebSocketInboundChannel implements WebSocketMessageBrokerConfigurer {

    private Counter reqCounter;
    private final MeterRegistry registry;

    @Value("${nextfeed.kubernetes.namespace}")
    private String NAMESPACE;

    @PostConstruct
    public void init(){
        reqCounter = registry.counter("%s_service_metrics".formatted(NAMESPACE), "usecase", "websocket-request");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                reqCounter.increment();
                return message;
            }
        });
    }

}