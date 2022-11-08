package com.nextfeed.service.core.survey.core;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.SurveyTemplateManagerServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@AllArgsConstructor
@GrpcService
public class SurveyTemplateManagerGRPCService extends SurveyTemplateManagerServiceGrpc.SurveyTemplateManagerServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(SurveyTemplateManagerGRPCService.class, args);
    }
    private final SurveyTemplateManager surveyTemplateManager;

    @Override
    public void createTemplate(DTOEntities.SurveyTemplateDTO dto, StreamObserver<DTOEntities.SurveyTemplateDTO> responseObserver) {
        dto = surveyTemplateManager.createTemplate(dto);
        responseObserver.onNext(dto);
        responseObserver.onCompleted();
    }

    @Override
    public void getTemplateById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSurveyTemplateDTO> responseObserver) {
        var dto = surveyTemplateManager.getTemplateById(request.getId());
        responseObserver.onNext(DTOEntities.OptionalSurveyTemplateDTO.newBuilder().setSurveyTemplate(dto.get()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllTemplates(Response.Empty e, StreamObserver<DTOEntities.SurveyTemplateDTOList> responseObserver) {
        var list = surveyTemplateManager.getAllTemplates();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

}
