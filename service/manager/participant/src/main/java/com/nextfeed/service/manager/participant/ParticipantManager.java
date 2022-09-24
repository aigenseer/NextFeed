package com.nextfeed.service.manager.participant;




import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.entity.session.Session;
import com.nextfeed.library.core.service.manager.SessionManagerService;
import com.nextfeed.library.core.service.repository.ParticipantRepositoryService;
import com.nextfeed.library.core.service.socket.SessionSocketServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ParticipantManager {

    private final SessionManagerService sessionManagerService;
    private final SessionSocketServices sessionSocketServices;
    private final ParticipantRepositoryService participantRepositoryService;

    public Participant createParticipantBySessionId(Integer sessionId, String nickname){
        Session session = sessionManagerService.getSessionById(sessionId);
        if(session != null){
            Participant participant = Participant.builder().nickname(nickname).session_id(sessionId).build();
            participant = participantRepositoryService.save(participant);
            sessionSocketServices.sendNewParticipantToAll(sessionId, participant);
            return participant;
        }
        return null;
    }

    public Session getSessionByParticipantId(int participantId){
        Participant participant = participantRepositoryService.findById(participantId);
        if(participant != null){
            return sessionManagerService.getSessionById(participant.getSession_id());
        }
        return null;
    }

    public Integer getSessionIdByParticipantId(int participantId){
        Session session = getSessionByParticipantId(participantId);
        if(session != null) return session.getId();
        return null;
    }

    public List<Participant> getParticipantsBySessionId(Integer sessionId){
        return participantRepositoryService.findBySessionId(sessionId);
    }

    public List<Participant> getConnectedParticipantsBySessionId(Integer sessionId){
        return getParticipantsBySessionId(sessionId).stream().filter(Participant::isConnected).toList();
    }

    public void updateConnectionStatusByParticipantId(Integer participant_id, Boolean status){
        Participant participant = participantRepositoryService.findById(participant_id);
        if(participant != null){
            participant.setConnected(status);
            participantRepositoryService.save(participant);
        }
    }

    public boolean existsParticipantId(int participantId){
        return participantRepositoryService.findById(participantId)!=null;
    }

    public Participant getParticipantById(int participantId){
        return participantRepositoryService.findById(participantId);
    }

}
