package com.nextfeed.service.supporting.management.user.core.participant.db;

import com.nextfeed.library.core.valueobject.participant.OptionalParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import com.nextfeed.library.core.entity.participant.Participant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ParticipantRepositoryService{

    private final ParticipantDBService participantDBService;

    public ParticipantValue save(ParticipantValue value) {
        return ParticipantValue.builder().entity(participantDBService.save(value.getEntity())).build();
    }

    public ParticipantValue save(Participant value) {
        return ParticipantValue.builder().entity(participantDBService.save(value)).build();
    }

    public ParticipantValue create(Integer sessionId, String nickname){
        return save(ParticipantValue.builder().entity(Participant.builder().session_id(sessionId).nickname(nickname).build()).build());
    }

    public OptionalParticipantValue findById(int id) {
        return OptionalParticipantValue.builder().entity(participantDBService.findById(id)).build();
    }

    public ParticipantValueList findBySessionId(Integer session_id) {
        return ParticipantValueList.createByEntities(participantDBService.getRepo().findBySessionId(session_id));
    }

    public void deleteAllBySessionId(Integer session_id) {
        participantDBService.getRepo().deleteAllBySessionId(session_id);
    }

}
