package com.nextfeed.service.core.question.adapter.primary.grpc;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.QuestionRepositoryServiceGrpc;
import com.nextfeed.library.core.proto.repository.VoteQuestionRequest;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.*;
import com.nextfeed.service.core.question.ports.incoming.IQuestionRepositoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class QuestionRepositoryGRPCService extends QuestionRepositoryServiceGrpc.QuestionRepositoryServiceImplBase {

    private final IQuestionRepositoryService questionRepositoryService;

    @Override
    public void save(DTOEntities.QuestionDTO dto, StreamObserver<DTOEntities.QuestionDTO> responseObserver) {
        responseObserver.onNext(questionRepositoryService.save(dto));
        responseObserver.onCompleted();
    }

    @Override
    public void findById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalQuestionDTO> responseObserver) {
        responseObserver.onNext(questionRepositoryService.findById(request));
        responseObserver.onCompleted();
    }

    @Override
    public void addVote(VoteQuestionRequest request, StreamObserver<Response.Empty> responseObserver) {
        questionRepositoryService.addVote(request);
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void findBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.QuestionDTOList> responseObserver) {
        responseObserver.onNext(questionRepositoryService.findBySessionId(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAllBySessionId(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        questionRepositoryService.deleteAllBySessionId(request);
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void close(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalQuestionDTO> responseObserver) {
        responseObserver.onNext(questionRepositoryService.close(request));
        responseObserver.onCompleted();
    }

}
