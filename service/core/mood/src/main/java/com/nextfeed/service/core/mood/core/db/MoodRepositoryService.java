package com.nextfeed.service.core.mood.core.db;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.proto.manager.NewMoodRequest;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.valueobject.mood.MoodValue;
import com.nextfeed.library.core.valueobject.mood.MoodValueList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MoodRepositoryService {

    private final MoodDBService moodDBService;

    public MoodValue save(MoodValue value) {
        return MoodValue.createByEntity(moodDBService.save(value.getEntity()));
    }

    public MoodValue create(int sessionId, NewMoodRequest request){
        var e = MoodEntity.builder().session_id(sessionId).value(request.getMoodValue()).participantsCount(request.getParticipantsCount()).timestamp(new Date().getTime()).build();
        return save(MoodValue.createByEntity(e));
    }

    public MoodValueList findBySessionId(Integer sessionId) {
        var list = moodDBService.getRepo().findBySessionId(sessionId);
        return MoodValueList.createByEntities(list);
    }

    public void deleteAllBySessionId(Requests.IDRequest request) {
        moodDBService.getRepo().deleteAllBySessionId(request.getId());
    }

}
