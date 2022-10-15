package com.nextfeed.service.manager.participant;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.CreateParticipantBySessionIdRequest;
import com.nextfeed.library.core.proto.manager.ParticipantManagerServiceGrpc;
import com.nextfeed.library.core.proto.manager.UpdateConnectionStatusByParticipantIdRequest;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;

import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@AllArgsConstructor
@GrpcService
public class ParticipantManagerGRPCService extends ParticipantManagerServiceGrpc.ParticipantManagerServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(ParticipantManagerGRPCService.class, args);
    }

    private final ParticipantManager participantManager;

    @Override
    public void createParticipantBySessionId(CreateParticipantBySessionIdRequest request, StreamObserver<DTOEntities.ParticipantDTO> responseObserver) {
        var dto = participantManager.createParticipantBySessionId(request.getSessionId(), request.getParticipant().getNickname());
        responseObserver.onNext(dto);
        responseObserver.onCompleted();
    }

    @Override
    public void getParticipantsBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.ParticipantDTOList> responseObserver) {
        var pList = participantManager.getParticipantsBySessionId(request.getId());
        var rely = DTOEntities.ParticipantDTOList.newBuilder().build();
        responseObserver.onNext(rely);
        responseObserver.onCompleted();
    }

    @Override
    public void getConnectedParticipantsBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.ParticipantDTOList> responseObserver) {
        var pList = participantManager.getConnectedParticipantsBySessionId(request.getId());
        var rely = DTOListUtils.toParticipantDTOList(pList);
        responseObserver.onNext(rely);
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
        var participantDTO = participantManager.getParticipantById(request.getId());
        responseObserver.onNext(DTOEntities.OptionalParticipantDTO.newBuilder().setParticipant(participantDTO).build());
        responseObserver.onCompleted();
    }

}
