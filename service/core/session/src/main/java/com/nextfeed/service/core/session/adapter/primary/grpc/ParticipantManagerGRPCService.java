package com.nextfeed.service.core.session.adapter.primary.grpc;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.CreateParticipantBySessionIdRequest;
import com.nextfeed.library.core.proto.manager.ParticipantManagerServiceGrpc;
import com.nextfeed.library.core.proto.manager.UpdateConnectionStatusByParticipantIdRequest;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.core.session.ports.incoming.usermanagement.IParticipantManager;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class ParticipantManagerGRPCService extends ParticipantManagerServiceGrpc.ParticipantManagerServiceImplBase {

    private final IParticipantManager participantManager;

    @Override
    public void getParticipantsBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.ParticipantDTOList> responseObserver) {
        var participantValueList = participantManager.getParticipantsBySessionId(request.getId());
        responseObserver.onNext(participantValueList.getDTOs());
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
