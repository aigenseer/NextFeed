package com.nextfeed.service.manager.participant;




import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.SessionManagerService;
import com.nextfeed.library.manager.repository.service.ParticipantDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ParticipantManager {

    private final SessionManagerService sessionManagerService;
    //todo: muss noch hinzugefügt werden
//    private final SessionDataService sessionDataService;
    private final ParticipantDBService participantDBService;

    public Participant createParticipantBySessionId(Integer sessionId, String nickname){
        Session session = sessionManagerService.getSessionById(sessionId);
        if(session != null){
            Participant participant = Participant.builder().nickname(nickname).session(session).build();
            participantDBService.save(participant);
            session.getParticipants().add(participant);
            sessionManagerService.saveSession(session);
//            sessionDataService.sendNewParticipantToAll(sessionId, participant);
            return participant;
        }
        return null;
    }

    public Session getSessionByParticipantId(int participantId){
        Participant participant = participantDBService.findById(participantId);
        if(participant != null){
            return participant.getSession();
        }
        return null;
    }

    public Integer getSessionIdByParticipantId(int participantId){
        Session session = getSessionByParticipantId(participantId);
        if(session != null) return session.getId();
        return null;
    }

    public List<Participant> getParticipantsBySessionId(Integer sessionId){
        return participantDBService.findBySession(sessionManagerService.getSessionById(sessionId));
    }

    public List<Participant> getConnectedParticipantsBySessionId(Integer sessionId){
        return getParticipantsBySessionId(sessionId).stream().filter(Participant::isConnected).toList();
    }

    public void updateConnectionStatusByParticipantId(Integer participant_id, Boolean status){
        Participant participant = participantDBService.findById(participant_id);
        if(participant != null){
            participant.setConnected(status);
            participantDBService.save(participant);
        }
    }

    public void checkParticipantId(Integer participantId){
        if (participantId == null || participantDBService.findById(participantId)==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Participant-Id %d are not exists", participantId));
    }

    public boolean existsParticipantId(int participantId){
        return participantDBService.findById(participantId)!=null;
    }




}
