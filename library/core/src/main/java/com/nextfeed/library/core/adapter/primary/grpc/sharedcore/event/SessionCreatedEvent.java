package com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event;

import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class SessionCreatedEvent extends ApplicationEvent {

    @Getter
    private int sessionId;
    @Getter
    private ParticipantValueList participantValueList;

    public SessionCreatedEvent(Object source, int sessionId, ParticipantValueList participantValueList) {
        super(source);
        this.sessionId = sessionId;
        this.participantValueList = participantValueList;
    }

}
