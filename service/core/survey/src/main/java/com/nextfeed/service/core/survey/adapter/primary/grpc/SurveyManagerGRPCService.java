package com.nextfeed.service.core.survey.adapter.primary.grpc;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.core.survey.ports.incoming.ISurveyManager;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class SurveyManagerGRPCService extends SurveyManagerServiceGrpc.SurveyManagerServiceImplBase {

    private final ISurveyManager surveyManager;

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
