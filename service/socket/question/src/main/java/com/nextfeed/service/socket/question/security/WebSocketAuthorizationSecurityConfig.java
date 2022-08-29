package com.nextfeed.service.socket.question.security;

import com.nextfeed.library.core.enums.UserRole;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketAuthorizationSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    @Override
    protected void configureInbound(final MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpDestMatchers("/socket/question-socket/v1/participant/**").hasRole(UserRole.PARTICIPANT.getRole())
                .simpDestMatchers("/socket/question-socket/v1/presenter/**").hasRole(UserRole.PRESENTER.getRole())
                .anyMessage().authenticated()
        ;
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
