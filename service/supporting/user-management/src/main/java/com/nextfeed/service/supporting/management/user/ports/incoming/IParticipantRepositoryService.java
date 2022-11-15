package com.nextfeed.service.supporting.management.user.ports.incoming;

import com.nextfeed.library.core.entity.participant.Participant;

import java.util.List;

public interface IParticipantRepositoryService {

    Participant save(Participant toAdd);
    Participant findById(int id);
    List<Participant> findBySessionId(Integer session_id);
    void deleteAllBySessionId(Integer session_id);

}
