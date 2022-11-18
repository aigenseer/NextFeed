package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.NewSessionRequest;
import com.nextfeed.library.core.proto.repository.SessionManagerServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionManagerServiceClient {

    @GrpcClient("session-service")
    private SessionManagerServiceGrpc.SessionManagerServiceBlockingStub rpcService;

    public DTOEntities.SessionDTO createSession(String name){
        return rpcService.createSession(NewSessionRequest.newBuilder().setName(name).build());
    }

    public void deleteSession(Integer id) {
        rpcService.deleteSession(Requests.IDRequest.newBuilder().setId(id).build());
    }

    public Optional<DTOEntities.SessionDTO> getSessionById(Integer id) {
        return Optional.of(rpcService.getSessionById(Requests.IDRequest.newBuilder().setId(id).build()).getSession());
    }

    public void closeSession(Integer id) {
        rpcService.closeSession(Requests.IDRequest.newBuilder().setId(id).build());
    }

    public boolean isSessionClosed(Integer id) {
        return rpcService.isSessionClosed(Requests.IDRequest.newBuilder().setId(id).build()).getResult();
    }

    public boolean existsSessionId(Integer id) {
        return rpcService.existsSessionId(Requests.IDRequest.newBuilder().setId(id).build()).getResult();
    }

    public DTOEntities.SessionDTOList getAllSessions() {
        return rpcService.getAllSessions(DTOResponseUtils.createEmpty());
    }

    public DTOEntities.SessionDTOList getAllOpenSessions() {
        return rpcService.getAllOpenSessions(DTOResponseUtils.createEmpty());
    }

    public DTOEntities.SessionDTOList getAllClosedSessions() {
        return rpcService.getAllClosedSessions(DTOResponseUtils.createEmpty());
    }

    public void closeAllOpenSessions() {
        rpcService.closeAllOpenSessions(DTOResponseUtils.createEmpty());
    }


}
