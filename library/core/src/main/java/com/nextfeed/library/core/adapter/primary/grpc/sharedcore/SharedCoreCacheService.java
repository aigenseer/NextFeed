package com.nextfeed.library.core.adapter.primary.grpc.sharedcore;

import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SharedCoreCacheService {
    @Getter
    private final Map<Integer, SessionCache> sessionCache = new HashMap<>();

    public boolean existsSessionId(Integer sessionId){
        return sessionCache.containsKey(sessionId);
    }

    public boolean existsParticipantIdBySessionId(Integer sessionId, Integer participantId){
        return existsSessionId(sessionId) && sessionCache.get(sessionId).existsParticipantById(participantId);
    }

    public Optional<ParticipantValue> getParticipantById(Integer sessionId, Integer participantId){
        if(existsSessionId(sessionId) && sessionCache.get(sessionId).existsParticipantById(participantId)){
            var participant = sessionCache.get(sessionId).getParticipants().get(participantId);
            return Optional.of(ParticipantValue.createByEntity(participant));
        }
        return Optional.empty();
    }

}
