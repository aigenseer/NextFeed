package com.nextfeed.service.core.session.adapter.primary;

import com.nextfeed.library.core.proto.repository.SendConnectionStatusRequest;
import com.nextfeed.library.core.proto.repository.SendMoodRequest;
import com.nextfeed.library.core.proto.repository.SendNewParticipantToAllRequest;
import com.nextfeed.library.core.proto.repository.SessionSocketServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.core.session.core.SessionDataService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class SessionSocketGRPCService extends SessionSocketServiceGrpc.SessionSocketServiceImplBase {

    private final SessionDataService sessionDataService;

    @Override
    public void sendNewParticipantToAll(SendNewParticipantToAllRequest request, StreamObserver<Response.Empty> responseObserver) {
        sessionDataService.sendNewParticipantToAll(request.getSessionId(), request.getParticipant());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void sendClose(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        sessionDataService.sendClose(request.getId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void sendConnectionStatus(SendConnectionStatusRequest request, StreamObserver<Response.Empty> responseObserver) {
        sessionDataService.sendConnectionStatus(request.getSessionId(), request.getParticipants().getParticipantsList());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }


}
