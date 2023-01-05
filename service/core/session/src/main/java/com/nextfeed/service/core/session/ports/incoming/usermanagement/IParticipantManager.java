package com.nextfeed.service.core.session.ports.incoming.usermanagement;

import com.nextfeed.library.core.valueobject.participant.OptionalParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;

public interface IParticipantManager {

    ParticipantValue createParticipantBySessionId(Integer sessionId, String nickname);
//    Optional<DTOEntities.SessionDTO> getSessionByParticipantId(int participantId);
    ParticipantValueList getParticipantsBySessionId(Integer sessionId);
//    ParticipantValueList getConnectedParticipantsBySessionId(Integer sessionId);
//    void updateConnectionStatusByParticipantId(Integer participantId, Boolean status);
    boolean existsParticipantId(int participantId);
    OptionalParticipantValue getParticipantById(int participantId);
}
