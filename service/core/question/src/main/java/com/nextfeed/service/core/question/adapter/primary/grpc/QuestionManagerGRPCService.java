package com.nextfeed.service.core.question.adapter.primary.grpc;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.QuestionManagerServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.service.core.question.ports.incoming.IQuestionManager;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class QuestionManagerGRPCService extends QuestionManagerServiceGrpc.QuestionManagerServiceImplBase {

    private final IQuestionManager questionManager;

    @Override
    public void findBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.QuestionDTOList> responseObserver) {
        var questionValueList = questionManager.findBySessionId(request.getId());
        responseObserver.onNext(questionValueList.getDTOs());
        responseObserver.onCompleted();
    }

}
