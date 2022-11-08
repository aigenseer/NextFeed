package com.nextfeed.service.supporting.management.user.core.participant;

import com.nextfeed.library.core.grpc.service.manager.SessionManagerServiceClient;
import com.nextfeed.library.core.grpc.service.repository.ParticipantRepositoryServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.service.socket.SessionSocketServices;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ParticipantManager {

    private final SessionManagerServiceClient sessionManagerServiceClient;
    private final SessionSocketServices sessionSocketServices;
    private final ParticipantRepositoryServiceClient participantRepositoryServiceClient;

    public DTOEntities.ParticipantDTO createParticipantBySessionId(Integer sessionId, String nickname){
        var session = sessionManagerServiceClient.getSessionById(sessionId);
        if(session.isPresent()){
            DTOEntities.ParticipantDTO participantDTO = DTOEntities.ParticipantDTO.newBuilder().setNickname(nickname).setSessionId(sessionId).build();
            participantDTO = participantRepositoryServiceClient.save(participantDTO);
            sessionSocketServices.sendNewParticipantToAll(sessionId, participantDTO);
            return participantDTO;
        }
        return null;
    }

    public Optional<DTOEntities.SessionDTO> getSessionByParticipantId(int participantId){
        DTOEntities.ParticipantDTO participantDTO = getParticipantById(participantId);
        if(participantDTO != null){
            return sessionManagerServiceClient.getSessionById(participantDTO.getSessionId());
        }
        return Optional.empty();
    }

    public Integer getSessionIdByParticipantId(int participantId){
        var session = getSessionByParticipantId(participantId);
        if(session.isPresent()) return session.get().getId();
        return null;
    }

    public DTOEntities.ParticipantDTOList getParticipantsBySessionId(Integer sessionId){
        return participantRepositoryServiceClient.findBySessionId(sessionId);
    }

    public List<DTOEntities.ParticipantDTO> getConnectedParticipantsBySessionId(Integer sessionId){
        return getParticipantsBySessionId(sessionId).getParticipantsList().stream().filter(DTOEntities.ParticipantDTO::getConnected).toList();
    }

    public void updateConnectionStatusByParticipantId(Integer participantId, Boolean status){
        var participantDTO = participantRepositoryServiceClient.findById(participantId);
        if(participantDTO.isPresent()){
            var participant = DTO2EntityUtils.dto2Participant(participantDTO.get());
            participant.setConnected(status);
            participantRepositoryServiceClient.save(Entity2DTOUtils.participant2DTO(participant));
        }
    }

    public boolean existsParticipantId(int participantId){
        return getParticipantById(participantId)!=null;
    }

    public DTOEntities.ParticipantDTO getParticipantById(int participantId){
        return participantRepositoryServiceClient.findById(participantId).orElseGet(null);
    }

}
