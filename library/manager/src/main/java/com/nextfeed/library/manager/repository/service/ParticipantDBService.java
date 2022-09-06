package com.nextfeed.library.manager.repository.service;


import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.manager.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantDBService extends AbstractService<Participant, ParticipantRepository> {
    public ParticipantDBService(ParticipantRepository participantRepository) {
        super(participantRepository);
    }

    public List<Participant> findBySessionId(Integer sessionId){
        return repo.findBySessionId(sessionId);
    }
}
