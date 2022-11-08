package com.nextfeed.service.core.survey.core.socket;

import com.nextfeed.library.core.proto.repository.*;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@RequiredArgsConstructor
@GrpcService
public class SurveySocketGRPCService extends SurveySocketServiceGrpc.SurveySocketServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(SurveySocketGRPCService.class, args);
    }

    private final SurveyService surveyService;

    @Override
    public void onCreateByPresenter(CreateByPresenterRequest request, StreamObserver<Response.Empty> responseObserver) {
        surveyService.onCreateByPresenter(request.getSessionId(), request.getSurvey());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void onCreateByParticipant(CreateByParticipantRequest request, StreamObserver<Response.Empty> responseObserver) {
        surveyService.onCreateByParticipant(request.getSessionId(), request.getSurveyId(), request.getTemplate());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void onClose(CloseRequest request, StreamObserver<Response.Empty> responseObserver) {
        surveyService.onClose(request.getSessionId(), request.getSurveyId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void onUpdate(UpdateRequest request, StreamObserver<Response.Empty> responseObserver) {
        surveyService.onUpdate(request.getSessionId(), request.getSurveyDTO());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void onResult(ResultRequest request, StreamObserver<Response.Empty> responseObserver) {
        surveyService.onResult(request.getSessionId(), request.getSurveyDTO());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }


}
