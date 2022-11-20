package com.nextfeed.service.core.session.ports.incoming;

import com.nextfeed.library.core.valueobject.session.OptionalSessionValue;
import com.nextfeed.library.core.valueobject.session.SessionValue;
import com.nextfeed.library.core.valueobject.session.SessionValueList;

public interface ISessionManager {

    boolean isSessionClosed(int sessionId);

    SessionValue createSession(String name);

    OptionalSessionValue getSessionById(Integer id);

    void closeSession(int sessionId);

    boolean existSessionById(Integer id);

    boolean existsOpenSessionById(Integer id);

    void deleteSession(int sessionId);

    SessionValueList getAllSessions();

    SessionValueList getAllOpenSessions();

    SessionValueList getAllClosedSessions();

    void closeAllOpenSessions();
}
