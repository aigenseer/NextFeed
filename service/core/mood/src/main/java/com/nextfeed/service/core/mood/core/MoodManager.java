package com.nextfeed.service.core.mood.core;

import com.nextfeed.library.core.grpc.service.manager.ParticipantManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.SessionManagerServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.NewCalculatedMoodRequest;
import com.nextfeed.library.core.proto.manager.NewMoodRequest;
import com.nextfeed.library.core.service.socket.MoodSocketServices;
import com.nextfeed.library.core.valueobject.mood.MoodValue;
import com.nextfeed.service.core.mood.core.db.MoodRepositoryService;
import com.nextfeed.service.core.mood.ports.incoming.IMoodManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MoodManager implements IMoodManager {

    private final MoodRepositoryService moodRepositoryService;
    private final SessionManagerServiceClient sessionManagerServiceClient;
    private final ParticipantManagerServiceClient participantManagerServiceClient;
    private final MoodSocketServices moodSocketServices;

    private final Map<Integer, Map<Integer, Double>> sessionsParticipantMoodValueCache = new HashMap<>();

    public MoodValue addMoodValueToSession(int sessionId, NewMoodRequest request){
        var moodValue = moodRepositoryService.create(sessionId, request);
        moodSocketServices.sendMood(sessionId, moodValue.getEntity().getValue());
        return moodValue;
    }

    private Map<Integer, Double> getParticipantMoodValueCacheBySessionId(int sessionId){
        if(sessionManagerServiceClient.isSessionClosed(sessionId)) return null;
        //todo: muss noch gemacht werden
//        List<Integer> connectedParticipantIds = participantManager.getConnectedParticipantsBySessionId(sessionId).stream().map(Participant::getId).toList();
        List<Integer> connectedParticipantIds = participantManagerServiceClient.getParticipantsBySessionId(sessionId).getParticipantsList().stream().map(DTOEntities.ParticipantDTO::getId).toList();
        if(!sessionsParticipantMoodValueCache.containsKey(sessionId)){
            Map<Integer, Double> cache = new HashMap<>();
            connectedParticipantIds.forEach(participantId -> cache.put(participantId, 0.0));
            sessionsParticipantMoodValueCache.put(sessionId, cache);
        }else{
            Map<Integer, Double> cache = sessionsParticipantMoodValueCache.get(sessionId);
            Set<Integer> cachedParticipantIds = cache.keySet();
            cachedParticipantIds.forEach(participantId -> {
                if(!connectedParticipantIds.contains(participantId)){
                    cache.remove(participantId);
                }
            });
            connectedParticipantIds.forEach(participantId -> {
                if(!cachedParticipantIds.contains(participantId)){
                    cache.put(participantId, 0.0);
                }
            });
            sessionsParticipantMoodValueCache.put(sessionId, cache);
        }
        return sessionsParticipantMoodValueCache.get(sessionId);
    }

    public MoodValue createCalculatedMoodValue(int sessionId, NewCalculatedMoodRequest request){
        Map<Integer, Double> cache = getParticipantMoodValueCacheBySessionId(sessionId);
        if(cache == null || !cache.containsKey(request.getParticipantId())) return null;
        cache.put(request.getParticipantId(), request.getMoodValue());
        sessionsParticipantMoodValueCache.put(sessionId, cache);
        if(cache.size() == 0) return addMoodValueToSession(sessionId, NewMoodRequest.newBuilder().setMoodValue(0).setParticipantsCount(0).build());
        var averageValue = ((double) cache.values().stream().mapToInt(Double::intValue).sum()) / cache.size();
        return addMoodValueToSession(sessionId, NewMoodRequest.newBuilder().setMoodValue(averageValue).setParticipantsCount(cache.size()).build());
    }

}
