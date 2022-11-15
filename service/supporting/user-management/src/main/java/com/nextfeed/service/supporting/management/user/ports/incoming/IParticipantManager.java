package com.nextfeed.service.supporting.management.user.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;

import java.util.List;
import java.util.Optional;

public interface IParticipantManager {

    DTOEntities.ParticipantDTO createParticipantBySessionId(Integer sessionId, String nickname);
    Optional<DTOEntities.SessionDTO> getSessionByParticipantId(int participantId);
    Integer getSessionIdByParticipantId(int participantId);
    DTOEntities.ParticipantDTOList getParticipantsBySessionId(Integer sessionId);
    List<DTOEntities.ParticipantDTO> getConnectedParticipantsBySessionId(Integer sessionId);
    void updateConnectionStatusByParticipantId(Integer participantId, Boolean status);
    boolean existsParticipantId(int participantId);
    DTOEntities.ParticipantDTO getParticipantById(int participantId);
}
