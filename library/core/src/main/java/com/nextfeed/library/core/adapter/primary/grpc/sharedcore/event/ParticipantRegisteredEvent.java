package com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event;

import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class ParticipantRegisteredEvent extends ApplicationEvent {

    @Getter
    private int sessionId;
    @Getter
    private ParticipantValue participantValue;

    public ParticipantRegisteredEvent(Object source, int sessionId, ParticipantValue participantValue) {
        super(source);
        this.sessionId = sessionId;
        this.participantValue = participantValue;
    }

}
