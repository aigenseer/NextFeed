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
import lombok.*;
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



    @Data
    private static class ParticipantCache{
        private Date lastUpdate = new Date();
        private Integer lastValue = 0;
    }

    private static class SessionCache{
        private final Map<Integer, ParticipantCache> cache = new HashMap();

        public synchronized void addParticipantValue(Integer participantId, Integer value){
            if(!cache.containsKey(participantId)) cache.put(participantId, new ParticipantCache());
            cache.get(participantId).setLastUpdate(new Date());
            cache.get(participantId).setLastValue(value);
        }
        public synchronized NewMoodRequest getNewMoodRequest(Date beforeDate){
            var participants = cache.values().stream().filter(p -> p.getLastUpdate().getTime() >=  beforeDate.getTime()).toList();
            var average = participants.stream().mapToDouble(ParticipantCache::getLastValue).average().orElse(0);
            return NewMoodRequest.builder().moodValue(average).participantsCount(participants.size()).build();

        }
        public Boolean existsParticipantId(Integer participantId){
            return cache.containsKey(participantId);
        }
    }

    private final Map<Integer, SessionCache> sessionsParticipantMoodValueCache = new HashMap<>();

    public MoodEntity addMoodValueToSession(int sessionId, NewMoodRequest request){
        System.out.println("New NewMoodRequest");
        MoodEntity moodEntity = MoodEntity.builder().value(request.getMoodValue()).session_id(sessionId).participantsCount(request.getParticipantsCount()).timestamp(new Date().getTime()).build();
        moodDBService.saveAsync(moodEntity);
        sessionSocketServices.sendMood(sessionId, moodEntity.getValue());
        System.out.println("sendMood");
        return moodEntity;
    }

    private void addParticipantValue(int sessionId, NewCalculatedMoodRequest request){
        if(!sessionsParticipantMoodValueCache.containsKey(sessionId)){
            sessionsParticipantMoodValueCache.put(sessionId, new SessionCache());
        }
        sessionsParticipantMoodValueCache.get(sessionId).addParticipantValue(request.getParticipantId(), request.getMoodValue());
    }

    private NewMoodRequest getSessionAverageMoodValue(int sessionId, Date beforeDate){
        return sessionsParticipantMoodValueCache.get(sessionId).getNewMoodRequest(beforeDate);
    }

    private boolean hasParticipantPermissionToSession(int sessionId, int participantId){
        SessionCache cache = sessionsParticipantMoodValueCache.get(sessionId);
        if(cache != null && cache.existsParticipantId(participantId)){
            return true;
        }
        return participantManager.getSessionIdByParticipantId(participantId) == sessionId;
    }

    public MoodEntity createCalculatedMoodValue(int sessionId, NewCalculatedMoodRequest request){
        if(!hasParticipantPermissionToSession(sessionId, request.getParticipantId())) return null;
        Calendar beforeDate = Calendar.getInstance();
        beforeDate.add(Calendar.MINUTE, 1);

        addParticipantValue(sessionId, request);
        NewMoodRequest newMoodRequest = getSessionAverageMoodValue(sessionId, beforeDate.getTime());
        return addMoodValueToSession(sessionId, newMoodRequest);
    }

}
