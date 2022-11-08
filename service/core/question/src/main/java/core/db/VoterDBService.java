package core.db;

import com.nextfeed.library.core.entity.question.VoterEntity;
import com.nextfeed.library.manager.repository.service.AbstractService;
import com.nextfeed.service.core.question.ports.outgoing.VoterRepository;
import org.springframework.stereotype.Service;

@Service
public class VoterDBService extends AbstractService<VoterEntity, VoterRepository> {
    public VoterDBService(VoterRepository voterRepository) {
        super(voterRepository);
    }
}
