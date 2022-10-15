package com.nextfeed.service.manager.mood;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.grpc.service.repository.MoodRepositoryServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.NewCalculatedMoodRequest;
import com.nextfeed.library.core.proto.manager.NewMoodRequest;
import com.nextfeed.library.core.service.manager.ParticipantManagerService;
import com.nextfeed.library.core.service.manager.SessionManagerService;
import com.nextfeed.library.core.service.socket.MoodSocketServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class MoodManager {

    private final MoodRepositoryServiceClient moodRepositoryServiceClient;
    private final SessionManagerService sessionManagerService;
    private final ParticipantManagerService participantManager;
    private final MoodSocketServices moodSocketServices;

    private final Map<Integer, Map<Integer, Double>> sessionsParticipantMoodValueCache = new HashMap<>();

    public DTOEntities.MoodEntityDTO addMoodValueToSession(int sessionId, NewMoodRequest request){
        DTOEntities.MoodEntityDTO dto = DTOEntities.MoodEntityDTO.newBuilder().setValue(request.getMoodValue()).setSessionId(sessionId).setParticipantsCount(request.getParticipantsCount()).setTimestamp(new Date().getTime()).build();
        moodRepositoryServiceClient.save(dto);
        moodSocketServices.sendMood(sessionId, dto.getValue());
        return dto;
    }

    private Map<Integer, Double> getParticipantMoodValueCacheBySessionId(int sessionId){
        if(sessionManagerService.isSessionClosed(sessionId)) return null;
        //todo: muss noch gemacht werden
//        List<Integer> connectedParticipantIds = participantManager.getConnectedParticipantsBySessionId(sessionId).stream().map(Participant::getId).toList();
        List<Integer> connectedParticipantIds = participantManager.getParticipantsBySessionId(sessionId).stream().map(Participant::getId).toList();
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

    public DTOEntities.MoodEntityDTO createCalculatedMoodValue(int sessionId, NewCalculatedMoodRequest request){
        Map<Integer, Double> cache = getParticipantMoodValueCacheBySessionId(sessionId);
        if(cache == null || !cache.containsKey(request.getParticipantId())) return null;
        cache.put(request.getParticipantId(), request.getMoodValue());
        sessionsParticipantMoodValueCache.put(sessionId, cache);
        if(cache.size() == 0) return addMoodValueToSession(sessionId, NewMoodRequest.newBuilder().setMoodValue(0).setParticipantsCount(0).build());
        var averageValue = ((double) cache.values().stream().mapToInt(Double::intValue).sum()) / cache.size();
        return addMoodValueToSession(sessionId, NewMoodRequest.newBuilder().setMoodValue(averageValue).setParticipantsCount(cache.size()).build());
    }

}
