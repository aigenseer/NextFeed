package com.nextfeed.service.core.session.ports.incoming;

import com.nextfeed.library.core.entity.session.SessionEntity;

import java.util.List;
import java.util.Optional;

public interface ISessionDBService {

    List<SessionEntity> findByClosed(long closed);

    List<SessionEntity> findAllOpen();

    List<SessionEntity> findAllClosed();

    SessionEntity save(SessionEntity toAdd);

    void deleteById(int id);

    List<SessionEntity> findAll();

    Optional<SessionEntity> findById(int id);
}
