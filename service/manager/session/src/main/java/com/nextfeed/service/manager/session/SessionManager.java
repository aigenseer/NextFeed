package com.nextfeed.service.manager.session;

import com.nextfeed.library.core.entity.session.Session;
import com.nextfeed.library.core.entity.session.SessionEntity;
import com.nextfeed.library.core.service.repository.SessionRepositoryService;
import com.nextfeed.library.core.service.socket.SessionSocketServices;
import com.nextfeed.library.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SessionManager {

    private final SessionSocketServices sessionSocketServices;
    private final SessionRepositoryService sessionRepositoryService;
    private static final int SESSION_CODE_LENGTH = 8;

    public boolean isSessionClosed(int sessionId){
        return getSessionById(sessionId).getClosed() != 0L;
    }

    public SessionEntity createSessionEntity(String name){
        return SessionEntity.builder().name(name).sessionCode(StringUtils.randomString(SESSION_CODE_LENGTH)).build();
    }

    public Session createSession(String name){
        SessionEntity session = createSessionEntity(name);
        var dto = sessionRepositoryService.save(session);
        return dto;
    }

    public SessionEntity getSessionById(Integer id) {
        return sessionRepositoryService.findById(id);
    }

    public Session getDTOById(Integer id) {
        return sessionRepositoryService.getById(id);
    }

    public Set<Integer> getAllSessionIds(){
        return sessionRepositoryService.findAll()
                .stream()
                .map(Session::getId)
                .collect(Collectors.toSet());
    }

    public void closeSession(int sessionId){
        SessionEntity session = getSessionById(sessionId);
        session.setClosed(new Date().getTime());
        sessionSocketServices.sendClose(sessionId);
        sessionRepositoryService.save(session);
    }

    public boolean existsSessionId(int sessionId){
        return getSessionById(sessionId) != null;
    }

    public void deleteSession(int sessionId){
        sessionRepositoryService.deleteById(sessionId);
    }

    public List<Session> getAllSessions(){
        return sessionRepositoryService.findAll();
    }

    public List<Session> getAllOpenSessions(){
        return sessionRepositoryService.findAllOpen();
    }

    public List<Session> getAllClosedSessions(){
        return sessionRepositoryService.findAllClosed();
    }

    public Session saveSession(SessionEntity session){
        return sessionRepositoryService.save(session);
    }

    public void closeAllOpenSessions(){
        sessionRepositoryService.findAllOpen().stream().forEach(session ->  closeSession(session.getId()));
    }



}
