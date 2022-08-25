package com.nextfeed.service.manager.session;




import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.entity.SessionMetadata;
import com.nextfeed.library.core.service.socket.SessionSocketServices;
import com.nextfeed.library.core.utils.StringUtils;
import com.nextfeed.library.manager.repository.service.SessionDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SessionManager {

    //Todo: muss noch integriert werden
    private final SessionSocketServices sessionSocketServices;
    private final SessionDBService sessionDBService;
    private static final int SESSION_CODE_LENGTH = 8;

    public boolean isSessionClosed(int sessionId){
        return getSessionById(sessionId).getClosed() != 0L;
    }

    public Session createSessionEntity(String name){
        return Session.builder().name(name).sessionCode(StringUtils.randomString(SESSION_CODE_LENGTH)).build();
    }

    public Session createSession(String name){
        Session session = createSessionEntity(name);
        sessionDBService.save(session);
        return session;
    }



    public boolean isCorrectSessionCode(Integer sessionId, String sessionCode){
        Session session = getSessionById(sessionId);
        return session != null && session.getSessionCode().equals(sessionCode);
    }

    public Session getSessionById(Integer id) {
        return sessionDBService.findById(id);
    }

    public Set<Integer> getAllSessionIds(){
        return sessionDBService.findAll()
                .stream()
                .map(Session::getId)
                .collect(Collectors.toSet());
    }


    public void closeSession(int sessionId){
        Session session = getSessionById(sessionId);
        session.setClosed(new Date().getTime());
        sessionSocketServices.sendClose(sessionId);
        sessionDBService.save(session);
    }




    public boolean existsSessionId(int sessionId){
        return getSessionById(sessionId) != null;
    }

    public void deleteSession(int sessionId){
        sessionDBService.deleteById(sessionId);
    }

    public List<Session> getAllSessions(){
        return sessionDBService.findAll();
    }

    public List<Session> getAllOpenSessions(){
        return sessionDBService.findAllOpen();
    }

    public List<Session> getAllClosedSessions(){
        return sessionDBService.findAllClosed();
    }

    public Session saveSession(Session session){
        return sessionDBService.save(session);
    }

    public void closeAllOpenSessions(){
        sessionDBService.findAllOpen().stream().forEach(session ->  closeSession(session.getId()));
    }



}
