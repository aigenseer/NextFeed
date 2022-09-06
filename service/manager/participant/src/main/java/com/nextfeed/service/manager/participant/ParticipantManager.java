package com.nextfeed.service.manager.participant;


import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.manager.SessionManagerService;
import com.nextfeed.library.core.service.socket.SessionSocketServices;
import com.nextfeed.library.manager.repository.service.ParticipantDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ParticipantManager {

    private final SessionManagerService sessionManagerService;
    private final SessionSocketServices sessionSocketServices;
    private final ParticipantDBService participantDBService;

    @Caching(evict = {
            @CacheEvict(value = "getParticipantById", key = "#sessionId")
    })
    public Participant createParticipantBySessionId(Integer sessionId, String nickname){
        if(sessionId != null){
            Participant participant = Participant.builder().nickname(nickname).session_id(sessionId).build();
            participantDBService.save(participant);
            sessionSocketServices.sendNewParticipantToAll(sessionId, participant);
            return participant;
        }
        return null;
    }

    public Session getSessionByParticipantId(int participantId){
        Participant participant = getParticipantById(participantId);
        if(participant != null && participant.getSession_id() != null){
            return sessionManagerService.getSessionById(participant.getSession_id());
        }
        return null;
    }

    @Cacheable(value = "getSessionIdByParticipantId", key = "#participantId")
    public Integer getSessionIdByParticipantId(int participantId){
        Session session = getSessionByParticipantId(participantId);
        if(session != null) return session.getId();
        return null;
    }


    public List<Participant> getParticipantsBySessionId(Integer sessionId){
        return participantDBService.findBySessionId(sessionId);
    }


    public List<Participant> getConnectedParticipantsBySessionId(Integer sessionId){
        return getParticipantsBySessionId(sessionId).stream().filter(Participant::isConnected).toList();
    }

    public void updateConnectionStatusByParticipantId(Integer participantId, Boolean status){
        Participant participant = getParticipantById(participantId);
        if(participant != null){
            participant.setConnected(status);
            participantDBService.save(participant);
        }
    }


    public boolean existsParticipantId(int participantId){
        return getParticipantById(participantId)!=null;
    }

    @Cacheable(value = "getParticipantById", key = "#participantId")
    public Participant getParticipantById(int participantId){
        return participantDBService.findById(participantId);
    }

}
