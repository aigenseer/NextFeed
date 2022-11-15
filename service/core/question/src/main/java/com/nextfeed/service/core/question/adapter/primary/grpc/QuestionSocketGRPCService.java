package com.nextfeed.service.core.question.adapter.primary.grpc;

import com.nextfeed.library.core.proto.repository.QuestionSocketServiceGrpc;
import com.nextfeed.library.core.proto.repository.SendQuestionRequest;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.core.question.ports.incoming.IQuestionService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class QuestionSocketGRPCService extends QuestionSocketServiceGrpc.QuestionSocketServiceImplBase {

    private final IQuestionService questionService;

    @Override
    public void sendQuestion(SendQuestionRequest request, StreamObserver<Response.Empty> responseObserver) {
        questionService.sendQuestion(request.getSessionId(), request.getQuestionDTO());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }


}
