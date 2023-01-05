package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.NewSessionRequest;
import com.nextfeed.library.core.proto.manager.SessionManagerServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.library.core.valueobject.session.SessionValueList;
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

    public boolean existsOpenSessionById(Integer id) {
        return rpcService.existsOpenSessionById(Requests.IDRequest.newBuilder().setId(id).build()).getResult();
    }

    public boolean existsSessionId(Integer id) {
        return rpcService.existsSessionId(Requests.IDRequest.newBuilder().setId(id).build()).getResult();
    }

    public SessionValueList getAllSessions() {
        return SessionValueList.createByDTO(rpcService.getAllSessions(DTOResponseUtils.createEmpty()));
    }


}
