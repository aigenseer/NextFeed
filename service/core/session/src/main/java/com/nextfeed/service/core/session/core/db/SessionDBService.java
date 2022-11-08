package com.nextfeed.service.core.session.core.db;

import com.nextfeed.library.core.entity.session.SessionEntity;
import com.nextfeed.library.manager.repository.service.AbstractService;
import com.nextfeed.service.core.session.ports.incoming.ISessionDBService;
import com.nextfeed.service.core.session.ports.outgoing.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionDBService extends AbstractService<SessionEntity, SessionRepository> implements ISessionDBService {

    public SessionDBService(SessionRepository sessionRepository) {
        super(sessionRepository);
    }
    public List<SessionEntity> findByClosed(long closed){
        return repo.findByClosed(closed);
    }

    public List<SessionEntity> findAllOpen(){
        return repo.findByClosed(0);
    }

    public List<SessionEntity> findAllClosed(){
        return repo.findAllClosed();
    }
}


