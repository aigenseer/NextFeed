package com.nextfeed.service.core.session.adapter.primary.grpc;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.SessionManagerServiceGrpc;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.service.core.session.ports.incoming.session.ISessionManager;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@AllArgsConstructor
@GrpcService
public class SessionManagerGRPCService extends SessionManagerServiceGrpc.SessionManagerServiceImplBase {

    private final ISessionManager sessionManager;

    @Override
    public void getAllSessions(Response.Empty e, StreamObserver<DTOEntities.SessionDTOList> responseObserver) {
        var list = sessionManager.getAllSessions();
        responseObserver.onNext(list.getDTOs());
        responseObserver.onCompleted();
    }








}
