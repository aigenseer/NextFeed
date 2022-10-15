package com.nextfeed.service.manager.question;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
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
public class QuestionManagerGRPCService extends QuestionManagerServiceGrpc.QuestionManagerServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(QuestionManagerGRPCService.class, args);
    }
    private final QuestionManager questionManager;

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
