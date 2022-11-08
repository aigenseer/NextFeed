package com.nextfeed.service.core.session.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;

import java.util.List;

public interface ISessionSocketService {

    void sendNewParticipantToAll(int sessionId, DTOEntities.ParticipantDTO participant);

    void sendClose(int sessionId);

    void sendConnectionStatus(int sessionId, List<DTOEntities.ParticipantDTO> participants);
}
