package com.nextfeed.service.core.session.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;

import java.util.Optional;
import java.util.Set;

public interface ISessionManager {

    boolean isSessionClosed(int sessionId);

    DTOEntities.SessionDTO createSession(String name);

    Optional<DTOEntities.SessionDTO> getSessionById(Integer id);

    Optional<DTOEntities.SessionEntityDTO> findEntityById(Integer id);

    Set<Integer> getAllSessionIds();

    void closeSession(int sessionId);

    boolean existsSessionId(int sessionId);

    void deleteSession(int sessionId);

    DTOEntities.SessionDTOList getAllSessions();

    DTOEntities.SessionDTOList getAllOpenSessions();

    DTOEntities.SessionDTOList getAllClosedSessions();

    void closeAllOpenSessions();
}
