package com.nextfeed.service.core.session.core.usermanagement.participant.db;


import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.manager.repository.service.AbstractService;
import com.nextfeed.service.core.session.ports.outgoing.usermanagement.ParticipantRepository;
import org.springframework.stereotype.Service;

@Service
public class ParticipantDBService extends AbstractService<Participant, ParticipantRepository> {
    public ParticipantDBService(ParticipantRepository participantRepository) {
        super(participantRepository);
    }
}
