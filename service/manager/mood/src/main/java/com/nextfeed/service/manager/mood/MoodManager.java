package com.nextfeed.service.manager.mood;




import com.nextfeed.library.core.entity.MoodEntity;
import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.manager.ParticipantManagerService;
import com.nextfeed.library.core.service.manager.SessionManagerService;
import com.nextfeed.library.core.service.manager.dto.mood.NewCalculatedMoodRequest;
import com.nextfeed.library.core.service.manager.dto.mood.NewMoodRequest;
import com.nextfeed.library.core.service.socket.SessionSocketServices;
import com.nextfeed.library.manager.repository.service.MoodDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class MoodManager {

    private final MoodDBService moodDBService;
    private final SessionManagerService sessionManagerService;
    private final ParticipantManagerService participantManager;
    //todo: muss noch gemacht werden
    private final SessionSocketServices sessionSocketServices;

    private final Map<Integer, Map<Integer, Integer>> sessionsParticipantMoodValueCache = new HashMap<>();

    public MoodEntity addMoodValueToSession(int sessionId, NewMoodRequest request){
        MoodEntity moodEntity = MoodEntity.builder().value(request.getMoodValue()).participantsCount(request.getParticipantsCount()).timestamp(new Date().getTime()).build();
        Session session = sessionManagerService.getSessionById(sessionId);
        moodDBService.save(moodEntity);
        session.getMoodEntities().add(moodEntity);
        sessionManagerService.saveSession(session);
        sessionSocketServices.sendMood(sessionId, moodEntity.getValue());
        return moodEntity;
    }

    private Map<Integer, Integer> getParticipantMoodValueCacheBySessionId(int sessionId){
        if(sessionManagerService.isSessionClosed(sessionId)) return null;
        //todo: muss noch gemacht werden
//        List<Integer> connectedParticipantIds = participantManager.getConnectedParticipantsBySessionId(sessionId).stream().map(Participant::getId).toList();
        List<Integer> connectedParticipantIds = participantManager.getParticipantsBySessionId(sessionId).stream().map(Participant::getId).toList();
        if(!sessionsParticipantMoodValueCache.containsKey(sessionId)){
            Map<Integer, Integer> cache = new HashMap<>();
            connectedParticipantIds.forEach(participantId -> cache.put(participantId, 0));
            sessionsParticipantMoodValueCache.put(sessionId, cache);
        }else{
            Map<Integer, Integer> cache = sessionsParticipantMoodValueCache.get(sessionId);
            Set<Integer> cachedParticipantIds = cache.keySet();
            cachedParticipantIds.forEach(participantId -> {
                if(!connectedParticipantIds.contains(participantId)){
                    cache.remove(participantId);
                }
            });
            connectedParticipantIds.forEach(participantId -> {
                if(!cachedParticipantIds.contains(participantId)){
                    cache.put(participantId, 0);
                }
            });
            sessionsParticipantMoodValueCache.put(sessionId, cache);
        }
        return sessionsParticipantMoodValueCache.get(sessionId);
    }

    public MoodEntity createCalculatedMoodValue(int sessionId, NewCalculatedMoodRequest request){
        Map<Integer, Integer> cache = getParticipantMoodValueCacheBySessionId(sessionId);
        if(cache == null || !cache.containsKey(request.getParticipantId())) return null;
        cache.put(request.getParticipantId(), request.getMoodValue());
        sessionsParticipantMoodValueCache.put(sessionId, cache);
        if(cache.size() == 0) return addMoodValueToSession(sessionId, new NewMoodRequest(0.0, 0));
        Double averageValue = ((double) cache.values().stream().mapToInt(Integer::intValue).sum()) / cache.size();
        return addMoodValueToSession(sessionId, new NewMoodRequest(averageValue, cache.size()));
    }

}
