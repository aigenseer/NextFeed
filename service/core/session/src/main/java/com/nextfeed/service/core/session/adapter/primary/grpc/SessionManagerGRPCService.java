package com.nextfeed.service.core.session.adapter.primary.grpc;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.NewSessionRequest;
import com.nextfeed.library.core.proto.repository.SessionManagerServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.core.session.core.SessionManager;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@AllArgsConstructor
@GrpcService
public class SessionManagerGRPCService extends SessionManagerServiceGrpc.SessionManagerServiceImplBase {

    private final SessionManager sessionManager;

    @Override
    public void createSession(NewSessionRequest request, StreamObserver<DTOEntities.SessionDTO> responseObserver){
        var e = sessionManager.createSession(request.getName());
        responseObserver.onNext(e);
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
        var dto = sessionManager.getSessionById(request.getId());
        responseObserver.onNext(DTOEntities.OptionalSessionDTO.newBuilder().setSession(dto.get()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void closeSession(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        sessionManager.closeSession(request.getId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void isSessionClosed(Requests.IDRequest request, StreamObserver<Response.BooleanResponse> responseObserver) {
        var b = sessionManager.isSessionClosed(request.getId());
        responseObserver.onNext(DTOResponseUtils.createBooleanResponse(b));
        responseObserver.onCompleted();
    }

    @Override
    public void existsSessionId(Requests.IDRequest request, StreamObserver<Response.BooleanResponse> responseObserver) {
        var b = sessionManager.existsSessionId(request.getId());
        responseObserver.onNext(DTOResponseUtils.createBooleanResponse(b));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllSessions(Response.Empty e, StreamObserver<DTOEntities.SessionDTOList> responseObserver) {
        var list = sessionManager.getAllSessions();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllOpenSessions(Response.Empty e, StreamObserver<DTOEntities.SessionDTOList> responseObserver) {
        var list = sessionManager.getAllOpenSessions();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllClosedSessions(Response.Empty e, StreamObserver<DTOEntities.SessionDTOList> responseObserver) {
        var list = sessionManager.getAllClosedSessions();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

    @Override
    public void closeAllOpenSessions(Response.Empty e, StreamObserver<Response.Empty> responseObserver) {
        sessionManager.closeAllOpenSessions();
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }



}
