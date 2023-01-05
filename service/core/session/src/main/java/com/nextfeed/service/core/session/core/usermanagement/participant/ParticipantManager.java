package com.nextfeed.service.core.session.core.usermanagement.participant;

import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.SharedCoreCacheService;
import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.service.external.SharedCoreServices;
import com.nextfeed.library.core.service.socket.SessionSocketServices;
import com.nextfeed.library.core.valueobject.participant.OptionalParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import com.nextfeed.service.core.session.core.usermanagement.participant.db.ParticipantRepositoryService;
import com.nextfeed.service.core.session.ports.incoming.usermanagement.IParticipantManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParticipantManager implements IParticipantManager {

    private final SharedCoreServices sharedCoreServices;
    private final SharedCoreCacheService sharedCoreCacheService;
    private final SessionSocketServices sessionSocketServices;
    private final ParticipantRepositoryService participantRepositoryServiceClient;

    public ParticipantValue createParticipantBySessionId(Integer sessionId, String nickname){
        if(sharedCoreCacheService.existsSessionId(sessionId)){
            var participantValue = participantRepositoryServiceClient.create(sessionId, nickname);
            sharedCoreServices.participantRegistered(sessionId, participantValue);
            sessionSocketServices.sendNewParticipantToAll(sessionId, participantValue);
            return participantValue;
        }
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
