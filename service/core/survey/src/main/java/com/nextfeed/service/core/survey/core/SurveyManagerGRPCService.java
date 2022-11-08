package com.nextfeed.service.core.survey.core;


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
public class SurveyManagerGRPCService extends SurveyManagerServiceGrpc.SurveyManagerServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(SurveyManagerGRPCService.class, args);
    }
    private final SurveyManager surveyManager;

    @Override
    public void createSurvey(CreateSurveyRequest request, StreamObserver<DTOEntities.SurveyTemplateDTO> responseObserver) {
        var dto = surveyManager.createSurvey(request.getSessionId(), request.getTemplate());
        responseObserver.onNext(dto);
        responseObserver.onCompleted();
    }

    @Override
    public void getSurveysBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.SurveyDTOList> responseObserver) {
        var dto = surveyManager.getSurveysBySessionId(request.getId());
        responseObserver.onNext(dto);
        responseObserver.onCompleted();
    }

    @Override
    public void addAnswerToSurvey(AddAnswerToSurveyRequest request, StreamObserver<Response.Empty> responseObserver) {
        surveyManager.addAnswerToSurvey(request.getSessionId(), request.getSurveyId(), request.getParticipantId(), request.getAnswer());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

}
