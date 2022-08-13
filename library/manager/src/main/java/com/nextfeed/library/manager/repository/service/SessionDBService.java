package com.nextfeed.library.manager.repository.service;

import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.manager.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionDBService extends AbstractService<Session, SessionRepository> {

    public SessionDBService(SessionRepository sessionRepository) {
        super(sessionRepository);
    }
    public List<Session> findByClosed(long closed){
        return repo.findByClosed(closed);
    }

    public List<Session> findAllOpen(){
        return repo.findByClosed(0);
    }

    public List<Session> findAllClosed(){
        return repo.findAllClosed();
    }
}


