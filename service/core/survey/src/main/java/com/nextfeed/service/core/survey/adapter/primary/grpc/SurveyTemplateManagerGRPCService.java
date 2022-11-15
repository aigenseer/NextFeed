package com.nextfeed.service.core.survey.adapter.primary.grpc;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.SurveyTemplateManagerServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.service.core.survey.core.SurveyTemplateManager;
import com.nextfeed.service.core.survey.ports.incoming.ISurveyRepositoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class SurveyTemplateManagerGRPCService extends SurveyTemplateManagerServiceGrpc.SurveyTemplateManagerServiceImplBase {

    private final ISurveyRepositoryService surveyRepositoryService;

    @Override
    public void createTemplate(DTOEntities.SurveyTemplateDTO dto, StreamObserver<DTOEntities.SurveyTemplateDTO> responseObserver) {
        dto = surveyRepositoryService.saveTemplate(dto);
        responseObserver.onNext(dto);
        responseObserver.onCompleted();
    }

    @Override
    public void getTemplateById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSurveyTemplateDTO> responseObserver) {
        var dto = surveyRepositoryService.findTemplateById(request.getId());
        responseObserver.onNext(DTOEntities.OptionalSurveyTemplateDTO.newBuilder().setSurveyTemplate(dto.getSurveyTemplate()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllTemplates(Response.Empty e, StreamObserver<DTOEntities.SurveyTemplateDTOList> responseObserver) {
        var list = surveyRepositoryService.findAllTemplates();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

}
