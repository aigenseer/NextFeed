package com.nextfeed.service.supporting.management.user.core.participant.db;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.valueobject.participant.OptionalParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ParticipantRepositoryService{

    private final ParticipantDBService participantDBService;

    public ParticipantValue save(ParticipantValue value) {
        return ParticipantValue.createByEntity(participantDBService.save(value.getEntity()));
    }

    public ParticipantValue save(Participant value) {
        return ParticipantValue.createByEntity(participantDBService.save(value));
    }

    public ParticipantValue create(Integer sessionId, String nickname){
        return save(ParticipantValue.createByEntity(Participant.builder().session_id(sessionId).nickname(nickname).build()));
    }

    public OptionalParticipantValue findById(int id) {
        return OptionalParticipantValue.createByOptionalEntity(participantDBService.findById(id));
    }

    public ParticipantValueList findBySessionId(Integer session_id) {
        return ParticipantValueList.createByEntities(participantDBService.getRepo().findBySessionId(session_id));
    }

    public void deleteAllBySessionId(Integer session_id) {
        participantDBService.getRepo().deleteAllBySessionId(session_id);
    }

}
