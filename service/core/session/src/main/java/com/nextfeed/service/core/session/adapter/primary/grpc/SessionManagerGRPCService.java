package com.nextfeed.service.core.session.adapter.primary.grpc;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.NewSessionRequest;
import com.nextfeed.library.core.proto.manager.SessionManagerServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.core.session.ports.incoming.session.ISessionManager;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@AllArgsConstructor
@GrpcService
public class SessionManagerGRPCService extends SessionManagerServiceGrpc.SessionManagerServiceImplBase {

    private final ISessionManager sessionManager;

    @Override
    public void createSession(NewSessionRequest request, StreamObserver<DTOEntities.SessionDTO> responseObserver){
        var sessionValue = sessionManager.createSession(request.getName());
        responseObserver.onNext(sessionValue.getDTO());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteSession(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        sessionManager.deleteSession(request.getId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void getSessionById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSessionDTO> responseObserver) {
        var optionalSessionValue = sessionManager.getSessionById(request.getId());
        responseObserver.onNext(optionalSessionValue.getOptionalDTO());
        responseObserver.onCompleted();
    }

    @Override
    public void closeSession(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        sessionManager.closeSession(request.getId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void existsSessionId(Requests.IDRequest request, StreamObserver<Response.BooleanResponse> responseObserver) {
        var b = sessionManager.existSessionById(request.getId());
        responseObserver.onNext(DTOResponseUtils.createBooleanResponse(b));
        responseObserver.onCompleted();
    }

    @Override
    public void existsOpenSessionById(Requests.IDRequest request, StreamObserver<Response.BooleanResponse> responseObserver) {
        var b = sessionManager.existsOpenSessionById(request.getId());
        responseObserver.onNext(DTOResponseUtils.createBooleanResponse(b));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllSessions(Response.Empty e, StreamObserver<DTOEntities.SessionDTOList> responseObserver) {
        var list = sessionManager.getAllSessions();
        responseObserver.onNext(list.getDTOs());
        responseObserver.onCompleted();
    }








}
