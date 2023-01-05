package com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class SessionClosedEvent extends ApplicationEvent {

    @Getter
    private int sessionId;

    public SessionClosedEvent(Object source, int sessionId) {
        super(source);
        this.sessionId = sessionId;
    }

}
