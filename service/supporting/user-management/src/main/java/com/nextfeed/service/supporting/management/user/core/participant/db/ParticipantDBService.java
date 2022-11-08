package com.nextfeed.service.supporting.management.user.core.participant.db;


import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.manager.repository.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantDBService extends AbstractService<Participant, ParticipantRepository> {
    public ParticipantDBService(ParticipantRepository participantRepository) {
        super(participantRepository);
    }
}
