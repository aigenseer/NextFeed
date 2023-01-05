package com.nextfeed.library.core.adapter.primary.grpc.sharedcore;

import com.nextfeed.library.core.proto.manager.ShareCache;
import com.nextfeed.library.core.proto.manager.ShareCacheResponse;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public ShareCacheResponse getSerializedCache(){
        var responseBuilder = ShareCacheResponse.newBuilder();
        List<ShareCache> list = new ArrayList<>();
        for (var session :sessionCache.values()) {
            var shareCacheBuilder = ShareCache.newBuilder();
            shareCacheBuilder.setSessionId(session.getSessionId());
            var participantValueList =ParticipantValueList.createByEntities(new ArrayList<>(session.getParticipants().values()));
            shareCacheBuilder.setParticipantDTOList(participantValueList.getDTOs());
            list.add(shareCacheBuilder.build());
        }
        responseBuilder.addAllShareCaches(list);
        return responseBuilder.build();
    }

}
