package com.nextfeed.library.core.adapter.primary.grpc.sharedcore;

import com.nextfeed.library.core.grpc.service.manager.SessionManagerServiceClient;
import com.nextfeed.library.core.proto.manager.ShareCache;
import com.nextfeed.library.core.proto.manager.ShareCacheResponse;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class SharedCoreCacheService {
    private Optional<ConcurrentHashMap<Integer, SessionCache>> sessionCaches = Optional.empty();
    private final SessionManagerServiceClient sessionManagerServiceClient;

    private void initSessionCaches(){
        if(sessionCaches.isPresent()) return;
        ConcurrentHashMap<Integer, SessionCache> cache = new ConcurrentHashMap<>();
        try{
            var response = sessionManagerServiceClient.getShareCache();
            for (var shareCache :response.getShareCachesList()) {
                var sessionCache = SessionCache.builder().sessionId(shareCache.getSessionId()).build();
                var participantValueList = ParticipantValueList.createByDTO(shareCache.getParticipantDTOList());
                for (var participant : participantValueList.getEntities()) {
                    sessionCache.addParticipant(participant);
                }
                cache.put(sessionCache.getSessionId(), sessionCache);
            }
        }catch (Exception e){
            System.out.println("Failed to initial session caches");
        }
        sessionCaches = Optional.of(cache);
    }

    public Map<Integer, SessionCache> getSessionsCache(){
        initSessionCaches();
        if (sessionCaches.isEmpty()){
            throw new RuntimeException("Failed to fetch the session cache");
        }
        return sessionCaches.get();
    }

    public boolean existsSessionId(Integer sessionId){
        return getSessionsCache().containsKey(sessionId);
    }

    public boolean existsParticipantIdBySessionId(Integer sessionId, Integer participantId){
        return existsSessionId(sessionId) && getSessionsCache().get(sessionId).existsParticipantById(participantId);
    }

    public Optional<ParticipantValue> getParticipantById(Integer sessionId, Integer participantId){
        if(existsSessionId(sessionId) && getSessionsCache().get(sessionId).existsParticipantById(participantId)){
            var participant = getSessionsCache().get(sessionId).getParticipants().get(participantId);
            return Optional.of(ParticipantValue.createByEntity(participant));
        }
        return Optional.empty();
    }

    public ShareCacheResponse getSerializedCache(){
        var responseBuilder = ShareCacheResponse.newBuilder();
        List<ShareCache> list = new ArrayList<>();
        if(sessionCaches.isPresent()){
            for (var session :sessionCaches.get().values()) {
                var shareCacheBuilder = ShareCache.newBuilder();
                shareCacheBuilder.setSessionId(session.getSessionId());
                var participantValueList = ParticipantValueList.createByEntities(new ArrayList<>(session.getParticipants().values()));
                shareCacheBuilder.setParticipantDTOList(participantValueList.getDTOs());
                list.add(shareCacheBuilder.build());
            }
        }
        responseBuilder.addAllShareCaches(list);
        return responseBuilder.build();
    }

}
