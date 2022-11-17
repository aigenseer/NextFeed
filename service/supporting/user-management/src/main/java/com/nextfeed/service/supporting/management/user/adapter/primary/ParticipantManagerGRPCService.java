package com.nextfeed.service.supporting.management.user.adapter.primary;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.CreateParticipantBySessionIdRequest;
import com.nextfeed.library.core.proto.manager.ParticipantManagerServiceGrpc;
import com.nextfeed.library.core.proto.manager.UpdateConnectionStatusByParticipantIdRequest;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.supporting.management.user.ports.incoming.IParticipantManager;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class ParticipantManagerGRPCService extends ParticipantManagerServiceGrpc.ParticipantManagerServiceImplBase {

    private final IParticipantManager participantManager;

    @Override
    public void createParticipantBySessionId(CreateParticipantBySessionIdRequest request, StreamObserver<DTOEntities.ParticipantDTO> responseObserver) {
        var participantValue = participantManager.createParticipantBySessionId(request.getSessionId(), request.getParticipant().getNickname());
        responseObserver.onNext(participantValue.getDTO());
        responseObserver.onCompleted();
    }

    @Override
    public void getParticipantsBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.ParticipantDTOList> responseObserver) {
        var participantValueList = participantManager.getParticipantsBySessionId(request.getId());
        responseObserver.onNext(participantValueList.getDTOs());
        responseObserver.onCompleted();
    }

    @Override
    public void getConnectedParticipantsBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.ParticipantDTOList> responseObserver) {
        var participantValueList = participantManager.getConnectedParticipantsBySessionId(request.getId());
        responseObserver.onNext(participantValueList.getDTOs());
        responseObserver.onCompleted();
    }

    @Override
    public void getSessionByParticipantId(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSessionDTO> responseObserver) {
        var session = participantManager.getSessionByParticipantId(request.getId());
        responseObserver.onNext(DTOEntities.OptionalSessionDTO.newBuilder().setSession(session.orElseGet(null)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getSessionIdByParticipantId(Requests.IDRequest request, StreamObserver<Response.IDResponse> responseObserver) {
        var sessionId = participantManager.getSessionIdByParticipantId(request.getId());
        responseObserver.onNext(DTOResponseUtils.createIDResponse(sessionId));
        responseObserver.onCompleted();
    }

    @Override
    public void updateConnectionStatusByParticipantId(UpdateConnectionStatusByParticipantIdRequest request, StreamObserver<Response.Empty> responseObserver) {
        participantManager.updateConnectionStatusByParticipantId(request.getParticipantId(), request.getStatus());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void existsParticipantId(Requests.IDRequest request, StreamObserver<Response.BooleanResponse> responseObserver) {
        var b = participantManager.existsParticipantId(request.getId());
        responseObserver.onNext(DTOResponseUtils.createBooleanResponse(b));
        responseObserver.onCompleted();
    }

    @Override
    public void getParticipant(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalParticipantDTO> responseObserver) {
        var optionalParticipantValue = participantManager.getParticipantById(request.getId());
        responseObserver.onNext(optionalParticipantValue.getOptionalDTO());
        responseObserver.onCompleted();
    }

}
