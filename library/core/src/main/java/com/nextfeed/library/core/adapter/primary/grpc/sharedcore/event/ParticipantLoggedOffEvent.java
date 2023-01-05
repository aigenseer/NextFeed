package com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class ParticipantLoggedOffEvent extends ApplicationEvent {

    @Getter
    private int sessionId;
    @Getter
    private int participantId;

    public ParticipantLoggedOffEvent(Object source, int sessionId, int participantId) {
        super(source);
        this.sessionId = sessionId;
        this.participantId = participantId;
    }

}
