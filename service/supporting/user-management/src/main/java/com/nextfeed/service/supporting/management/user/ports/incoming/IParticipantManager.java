package com.nextfeed.service.supporting.management.user.ports.incoming;

import com.nextfeed.library.core.dto.participant.OptionalParticipantValue;
import com.nextfeed.library.core.dto.participant.ParticipantValue;
import com.nextfeed.library.core.dto.participant.ParticipantValueList;
import com.nextfeed.library.core.proto.entity.DTOEntities;

import java.util.Optional;

public interface IParticipantManager {

    ParticipantValue createParticipantBySessionId(Integer sessionId, String nickname);
    Optional<DTOEntities.SessionDTO> getSessionByParticipantId(int participantId);
    Integer getSessionIdByParticipantId(int participantId);
    ParticipantValueList getParticipantsBySessionId(Integer sessionId);
    ParticipantValueList getConnectedParticipantsBySessionId(Integer sessionId);
    void updateConnectionStatusByParticipantId(Integer participantId, Boolean status);
    boolean existsParticipantId(int participantId);
    OptionalParticipantValue getParticipantById(int participantId);
}
