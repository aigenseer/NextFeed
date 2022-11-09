package com.nextfeed.service.core.session.adapter.primary.grpc;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.*;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.core.session.ports.incoming.ISessionRepositoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class SessionRepositoryGRPCService extends SessionRepositoryServiceGrpc.SessionRepositoryServiceImplBase {

    private final ISessionRepositoryService sessionRepositoryService;

    @Override
    public void save(DTOEntities.SessionEntityDTO dto, StreamObserver<DTOEntities.SessionDTO> responseObserver) {
        responseObserver.onNext(sessionRepositoryService.save(dto));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteById(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        sessionRepositoryService.deleteById(request);
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(Response.Empty e, StreamObserver<DTOEntities.SessionDTOList> responseObserver) {
        responseObserver.onNext(sessionRepositoryService.findAll());
        responseObserver.onCompleted();
    }

    @Override
    public void findAllOpen(Response.Empty e, StreamObserver<DTOEntities.SessionDTOList> responseObserver) {
        responseObserver.onNext(sessionRepositoryService.findAllOpen());
        responseObserver.onCompleted();
    }

    @Override
    public void findAllClosed(Response.Empty e, StreamObserver<DTOEntities.SessionDTOList> responseObserver) {
        responseObserver.onNext(sessionRepositoryService.findAllClosed());
        responseObserver.onCompleted();
    }

    @Override
    public void findById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSessionDTO> responseObserver) {
        responseObserver.onNext(sessionRepositoryService.findById(request));
        responseObserver.onCompleted();
    }

    @Override
    public void findEntityById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSessionEntityDTO> responseObserver) {
        responseObserver.onNext(sessionRepositoryService.findEntityById(request));
        responseObserver.onCompleted();
    }

    @Override
    public void close(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSessionEntityDTO> responseObserver) {
        responseObserver.onNext(sessionRepositoryService.close(request));
        responseObserver.onCompleted();
    }

}
