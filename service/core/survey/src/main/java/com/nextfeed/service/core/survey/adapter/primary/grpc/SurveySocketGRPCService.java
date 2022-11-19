package com.nextfeed.service.core.survey.adapter.primary.grpc;

import com.nextfeed.library.core.proto.repository.*;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.library.core.valueobject.survey.SurveyValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;
import com.nextfeed.service.core.survey.ports.incoming.ISurveyService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class SurveySocketGRPCService extends SurveySocketServiceGrpc.SurveySocketServiceImplBase {

    private final ISurveyService surveyService;

    @Override
    public void onCreateByPresenter(CreateByPresenterRequest request, StreamObserver<Response.Empty> responseObserver) {
        surveyService.onCreateByPresenter(request.getSessionId(), SurveyValue.createByDTO(request.getSurvey()));
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void onCreateByParticipant(CreateByParticipantRequest request, StreamObserver<Response.Empty> responseObserver) {
        surveyService.onCreateByParticipant(request.getSessionId(), request.getSurveyId(), SurveyTemplateValue.createByDTO(request.getTemplate()));
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
        surveyService.onUpdate(request.getSessionId(), SurveyValue.createByDTO(request.getSurveyDTO()));
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void onResult(ResultRequest request, StreamObserver<Response.Empty> responseObserver) {
        surveyService.onResult(request.getSessionId(), SurveyValue.createByDTO(request.getSurveyDTO()));
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }


}
