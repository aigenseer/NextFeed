package com.nextfeed.service.core.mood.core.db;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.NewMoodRequest;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.valueobject.mood.MoodValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MoodRepositoryService {

    private final MoodDBService moodDBService;

    public MoodValue save(MoodValue value) {
        return MoodValue.builder().entity(moodDBService.save(value.getEntity())).build();
    }

    public MoodValue create(int sessionId, NewMoodRequest request){
        var e = MoodEntity.builder().session_id(sessionId).value(request.getMoodValue()).participantsCount(request.getParticipantsCount()).timestamp(new Date().getTime()).build();
        return save(MoodValue.builder().entity(e).build());
    }

    public DTOEntities.MoodEntityDTOList findBySessionId(Requests.IDRequest request) {
        var list = moodDBService.getRepo().findBySessionId(request.getId());
        return DTOListUtils.moodEntities2DTO(list);
    }

    public void deleteAllBySessionId(Requests.IDRequest request) {
        moodDBService.getRepo().deleteAllBySessionId(request.getId());
    }

}
