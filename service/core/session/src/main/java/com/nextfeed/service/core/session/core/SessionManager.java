package com.nextfeed.service.core.session.core;

import com.nextfeed.library.core.entity.session.SessionContainer;
import com.nextfeed.library.core.entity.session.SessionEntity;
import com.nextfeed.library.core.service.socket.SessionSocketServices;
import com.nextfeed.library.core.utils.StringUtils;
import com.nextfeed.library.core.valueobject.session.OptionalSessionValue;
import com.nextfeed.library.core.valueobject.session.SessionValue;
import com.nextfeed.library.core.valueobject.session.SessionValueList;
import com.nextfeed.service.core.session.core.db.SessionRepositoryService;
import com.nextfeed.service.core.session.ports.incoming.ISessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SessionManager implements ISessionManager {

    private final SessionSocketServices sessionSocketServices;
    private final SessionRepositoryService sessionRepositoryService;
    private static final int SESSION_CODE_LENGTH = 8;

    public boolean isSessionClosed(int sessionId){
        var optionalSessionValue = getSessionById(sessionId);
        if (!optionalSessionValue.isPresent()) return true;
        return optionalSessionValue.get().getEntity().getClosed() != 0L;
    }

    private SessionEntity createSessionEntityDTO(String name){
        return SessionEntity.builder()
                .name(name)
                .closed(0)
                .sessionCode(StringUtils.randomString(SESSION_CODE_LENGTH))
                .build();
    }

    public SessionValue createSession(String name){
        return sessionRepositoryService.save(createSessionEntityDTO(name));
    }

    public OptionalSessionValue getSessionById(Integer id) {
        return sessionRepositoryService.findById(id);
    }

    public boolean existSessionById(Integer id) {
        return sessionRepositoryService.existsById(id);
    }

    public boolean existsOpenSessionById(Integer id) {
        return sessionRepositoryService.existsOpenSessionById(id);
    }

    public Set<Integer> getAllSessionIds(){
        return sessionRepositoryService.findAll().getEntities()
                .stream()
                .map(SessionContainer::getId)
                .collect(Collectors.toSet());
    }

    public void closeSession(int sessionId){
        var optionalSessionValue = sessionRepositoryService.findById(sessionId);
        if (optionalSessionValue.isPresent()){
            var entity = optionalSessionValue.get().getEntity();
            sessionSocketServices.sendClose(sessionId);
        }
    }

    public void deleteSession(int sessionId){
        sessionRepositoryService.deleteById(sessionId);
    }

    public SessionValueList getAllSessions(){
        return sessionRepositoryService.findAll();
    }

    public SessionValueList getAllOpenSessions(){
        return sessionRepositoryService.findAllOpen();
    }

    public SessionValueList getAllClosedSessions(){
        return sessionRepositoryService.findAllClosed();
    }

    public void closeAllOpenSessions(){
        sessionRepositoryService.findAllOpen().getEntities().forEach(session ->  closeSession(session.getId()));
    }



}
