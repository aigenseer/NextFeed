package com.nextfeed.service.core.session.core;

import com.nextfeed.library.core.grpc.service.repository.SessionRepositoryServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.service.socket.SessionSocketServices;
import com.nextfeed.library.core.utils.StringUtils;
import com.nextfeed.service.core.session.ports.incoming.ISessionManager;
import com.nextfeed.service.core.session.ports.incoming.ISessionRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SessionManager implements ISessionManager {

    private final SessionSocketServices sessionSocketServices;
    private final ISessionRepositoryService sessionRepositoryServiceClient;
    private static final int SESSION_CODE_LENGTH = 8;

    public boolean isSessionClosed(int sessionId){
        var dto = getSessionById(sessionId);
        if (!dto.isInitialized()) return true;
        return dto.getSession().getClosed() != 0L;
    }

    private DTOEntities.SessionEntityDTO createSessionEntityDTO(String name){
        return DTOEntities.SessionEntityDTO.newBuilder()
                .setName(name)
                .setClosed(0)
                .setSessionCode(StringUtils.randomString(SESSION_CODE_LENGTH))
                .build();
    }

    public DTOEntities.SessionDTO createSession(String name){
        var dto = createSessionEntityDTO(name);
        return sessionRepositoryServiceClient.save(dto);
    }

    public DTOEntities.OptionalSessionDTO getSessionById(Integer id) {
        return sessionRepositoryServiceClient.findById(id);
    }

    public DTOEntities.OptionalSessionEntityDTO findEntityById(Integer id) {
        return sessionRepositoryServiceClient.findEntityById(id);
    }

    public Set<Integer> getAllSessionIds(){
        return sessionRepositoryServiceClient.findAll().getSessionsList()
                .stream()
                .map(DTOEntities.SessionDTO::getId)
                .collect(Collectors.toSet());
    }

    public void closeSession(int sessionId){
        var dto = sessionRepositoryServiceClient.close(sessionId);
        if (dto.isInitialized()){
            sessionSocketServices.sendClose(sessionId);
        }
    }

    public boolean existsSessionId(int sessionId){
        return getSessionById(sessionId).isInitialized();
    }

    public void deleteSession(int sessionId){
        sessionRepositoryServiceClient.deleteById(sessionId);
    }

    public DTOEntities.SessionDTOList getAllSessions(){
        return sessionRepositoryServiceClient.findAll();
    }

    public DTOEntities.SessionDTOList getAllOpenSessions(){
        return sessionRepositoryServiceClient.findAllOpen();
    }

    public DTOEntities.SessionDTOList getAllClosedSessions(){
        return sessionRepositoryServiceClient.findAllClosed();
    }

    public void closeAllOpenSessions(){
        sessionRepositoryServiceClient.findAllOpen().getSessionsList().forEach(session ->  closeSession(session.getId()));
    }



}
