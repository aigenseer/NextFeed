package com.nextfeed.service.core.survey.adapter.primary.grpc;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.SurveyTemplateManagerServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;
import com.nextfeed.service.core.survey.ports.incoming.ISurveyManager;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class SurveyTemplateManagerGRPCService extends SurveyTemplateManagerServiceGrpc.SurveyTemplateManagerServiceImplBase {

    private final ISurveyManager surveyManager;

    @Override
    public void createTemplate(DTOEntities.SurveyTemplateDTO dto, StreamObserver<DTOEntities.SurveyTemplateDTO> responseObserver) {
        var surveyTemplate = SurveyTemplateValue.createByDTO(dto);
        surveyTemplate  = surveyManager.saveTemplate(surveyTemplate.getEntity());
        responseObserver.onNext(surveyTemplate.getDTO());
        responseObserver.onCompleted();
    }

    @Override
    public void getTemplateById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSurveyTemplateDTO> responseObserver) {
        var optionalSurveyTemplateValue = surveyManager.findTemplateById(request.getId());
        responseObserver.onNext(optionalSurveyTemplateValue.getOptionalDTO());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllTemplates(Response.Empty e, StreamObserver<DTOEntities.SurveyTemplateDTOList> responseObserver) {
        var list = surveyManager.findAllTemplates();
        responseObserver.onNext(list.getDTOs());
        responseObserver.onCompleted();
    }

}
