package com.nextfeed.service.supporting.management.user.core.participant.db;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.service.supporting.management.user.ports.incoming.IParticipantRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ParticipantRepositoryService implements IParticipantRepositoryService {

    private final ParticipantDBService participantDBService;

    @Override
    public Participant save(Participant toAdd) {
        return participantDBService.save(toAdd);
    }

    @Override
    public Participant findById(int id) {
        return participantDBService.findById(id);
    }

    @Override
    public List<Participant> findBySessionId(Integer session_id) {
        return participantDBService.getRepo().findBySessionId(session_id);
    }

    @Override
    public void deleteAllBySessionId(Integer session_id) {
        participantDBService.getRepo().deleteAllBySessionId(session_id);
    }

}
