package com.nextfeed.service.core.survey.adapter.primary.grpc;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.AddAnswerToSurveyRequest;
import com.nextfeed.library.core.proto.manager.CreateSurveyRequest;
import com.nextfeed.library.core.proto.manager.SurveyManagerServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;
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
        var surveyTemplateValue = surveyManager.createSurvey(request.getSessionId(), SurveyTemplateValue.dtoBuilder().dto(request.getTemplate()).build());
        responseObserver.onNext(surveyTemplateValue.getDTO());
        responseObserver.onCompleted();
    }

    @Override
    public void getSurveysBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.SurveyDTOList> responseObserver) {
        var surveyValueList = surveyManager.getSurveysBySessionId(request.getId());
        responseObserver.onNext(surveyValueList.getDTOs());
        responseObserver.onCompleted();
    }

    @Override
    public void addAnswerToSurvey(AddAnswerToSurveyRequest request, StreamObserver<Response.Empty> responseObserver) {
        surveyManager.addAnswerToSurvey(request.getSessionId(), request.getSurveyId(), request.getParticipantId(), request.getAnswer());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

}
