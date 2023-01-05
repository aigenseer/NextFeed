package com.nextfeed.service.core.session.ports.incoming.session;

import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;

public interface ISessionSocketService {

    void sendNewParticipantToAll(int sessionId, ParticipantValue participant);

    void sendClose(int sessionId);

    void sendConnectionStatus(int sessionId, ParticipantValueList participants);
}
