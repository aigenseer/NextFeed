package com.nextfeed.service.repository.question;

import com.nextfeed.library.core.entity.question.VoterEntity;
import com.nextfeed.library.manager.repository.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class VoterDBService extends AbstractService<VoterEntity, VoterRepository> {
    public VoterDBService(VoterRepository voterRepository) {
        super(voterRepository);
    }
}
