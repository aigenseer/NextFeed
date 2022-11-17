package com.nextfeed.service.core.session.adapter.primary.grpc;

import com.nextfeed.library.core.proto.repository.SendConnectionStatusRequest;
import com.nextfeed.library.core.proto.repository.SendNewParticipantToAllRequest;
import com.nextfeed.library.core.proto.repository.SessionSocketServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.core.session.ports.incoming.ISessionSocketService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class SessionSocketGRPCService extends SessionSocketServiceGrpc.SessionSocketServiceImplBase {

    private final ISessionSocketService sessionSocketService;

    @Override
    public void sendNewParticipantToAll(SendNewParticipantToAllRequest request, StreamObserver<Response.Empty> responseObserver) {
        sessionSocketService.sendNewParticipantToAll(request.getSessionId(), request.getParticipant());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void sendClose(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        sessionSocketService.sendClose(request.getId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void sendConnectionStatus(SendConnectionStatusRequest request, StreamObserver<Response.Empty> responseObserver) {
        sessionSocketService.sendConnectionStatus(request.getSessionId(), request.getParticipants().getParticipantsList());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }


}