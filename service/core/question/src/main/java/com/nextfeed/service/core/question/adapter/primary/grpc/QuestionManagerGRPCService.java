package com.nextfeed.service.core.question.adapter.primary.grpc;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.core.question.ports.incoming.IQuestionManager;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class QuestionManagerGRPCService extends QuestionManagerServiceGrpc.QuestionManagerServiceImplBase {

    private final IQuestionManager questionManager;

    @Override
    public void existsQuestionId(Requests.IDRequest request, StreamObserver<Response.BooleanResponse> responseObserver) {
        var b = questionManager.existsQuestionId(request.getId());
        responseObserver.onNext(DTOResponseUtils.createBooleanResponse(b));
        responseObserver.onCompleted();
    }

    @Override
    public void createQuestion(CreateQuestionRequest request, StreamObserver<DTOEntities.QuestionDTO> responseObserver) {
        var dto = questionManager.createQuestion(request.getSessionId(), request.getRequest());
        responseObserver.onNext(dto);
        responseObserver.onCompleted();
    }

    @Override
    public void ratingUpByQuestionId(RatingUpByQuestionIdRequest request, StreamObserver<Response.Empty> responseObserver) {
        questionManager.ratingUpByQuestionId(request.getSessionId(), request.getQuestionId(), request.getParticipantId(), request.getRating());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void closeQuestion(CloseQuestionRequest request, StreamObserver<Response.Empty> responseObserver) {
        questionManager.closeQuestion(request.getSessionId(), request.getQuestionId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

}
