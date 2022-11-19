package com.nextfeed.service.supporting.management.user.core.participant;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.grpc.service.manager.SessionManagerServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.service.socket.SessionSocketServices;
import com.nextfeed.library.core.valueobject.participant.OptionalParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import com.nextfeed.service.supporting.management.user.core.participant.db.ParticipantRepositoryService;
import com.nextfeed.service.supporting.management.user.ports.incoming.IParticipantManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ParticipantManager implements IParticipantManager {

    private final SessionManagerServiceClient sessionManagerServiceClient;
    private final SessionSocketServices sessionSocketServices;
    private final ParticipantRepositoryService participantRepositoryServiceClient;

    public ParticipantValue createParticipantBySessionId(Integer sessionId, String nickname){
        var session = sessionManagerServiceClient.getSessionById(sessionId);
        if(session.isPresent()){
            var participantValue = participantRepositoryServiceClient.create(sessionId, nickname);
            sessionSocketServices.sendNewParticipantToAll(sessionId, participantValue);
            return participantValue;
        }
        return null;
    }

    public Optional<DTOEntities.SessionDTO> getSessionByParticipantId(int participantId){
        var participantValue = getParticipantById(participantId);
        if(participantValue.isPresent()){
            return sessionManagerServiceClient.getSessionById(participantId);
        }
        return Optional.empty();
    }

    public Integer getSessionIdByParticipantId(int participantId){
        var session = getSessionByParticipantId(participantId);
        if(session.isPresent()) return session.get().getId();
        return null;
    }

    public ParticipantValueList getParticipantsBySessionId(Integer sessionId){
        return participantRepositoryServiceClient.findBySessionId(sessionId);
    }

    public ParticipantValueList getConnectedParticipantsBySessionId(Integer sessionId){
        return ParticipantValueList.createByEntities(getParticipantsBySessionId(sessionId).getEntities().stream().filter(Participant::isConnected).toList());
    }

    public void updateConnectionStatusByParticipantId(Integer participantId, Boolean status){
        var participantValue = participantRepositoryServiceClient.findById(participantId);
        if(participantValue.isPresent()){
            var entity = participantValue.get().getEntity();
            entity.setConnected(status);
            participantRepositoryServiceClient.save(entity);
        }
    }

    public boolean existsParticipantId(int participantId){
        return getParticipantById(participantId).isPresent();
    }

    public OptionalParticipantValue getParticipantById(int participantId){
        return participantRepositoryServiceClient.findById(participantId);
    }

}
