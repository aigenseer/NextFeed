package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.manager.SessionManagerServiceGrpc;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.library.core.valueobject.session.SessionValueList;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class SessionManagerServiceClient {

    @GrpcClient("session-service")
    private SessionManagerServiceGrpc.SessionManagerServiceBlockingStub rpcService;

    public SessionValueList getAllSessions() {
        return SessionValueList.createByDTO(rpcService.getAllSessions(DTOResponseUtils.createEmpty()));
    }


}
