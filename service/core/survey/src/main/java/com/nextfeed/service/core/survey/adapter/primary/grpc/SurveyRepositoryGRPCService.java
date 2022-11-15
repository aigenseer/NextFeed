package com.nextfeed.service.core.survey.adapter.primary.grpc;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.ExistsSurveyAnswerByParticipantRequest;
import com.nextfeed.library.core.proto.repository.SurveyRepositoryServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import com.nextfeed.service.core.survey.core.db.SurveyAnswerDBService;
import com.nextfeed.service.core.survey.core.db.SurveyDBService;
import com.nextfeed.service.core.survey.core.db.SurveyRepositoryService;
import com.nextfeed.service.core.survey.core.db.SurveyTemplateDBService;
import com.nextfeed.service.core.survey.ports.incoming.ISurveyRepositoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;

@RequiredArgsConstructor
@GrpcService
public class SurveyRepositoryGRPCService extends SurveyRepositoryServiceGrpc.SurveyRepositoryServiceImplBase {

    private final ISurveyRepositoryService surveyRepositoryService;

    @Override
    public void saveSurvey(DTOEntities.SurveyDTO dto, StreamObserver<DTOEntities.SurveyDTO> responseObserver) {
        responseObserver.onNext(surveyRepositoryService.saveSurvey(dto));
        responseObserver.onCompleted();
    }

    @Override
    public void findSurveyById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSurveyDTO> responseObserver) {
        responseObserver.onNext(surveyRepositoryService.findSurveyById(request));
        responseObserver.onCompleted();
    }

    @Override
    public void saveAnswer(DTOEntities.SurveyAnswerDTO dto, StreamObserver<DTOEntities.SurveyAnswerDTO> responseObserver) {
        responseObserver.onNext(surveyRepositoryService.saveAnswer(dto));
        responseObserver.onCompleted();
    }

    @Override
    public void existsSurveyAnswerByParticipant(ExistsSurveyAnswerByParticipantRequest request, StreamObserver<Response.BooleanResponse> responseObserver) {
        responseObserver.onNext(surveyRepositoryService.existsSurveyAnswerByParticipant(request));
        responseObserver.onCompleted();
    }

    @Override
    public void saveTemplate(DTOEntities.SurveyTemplateDTO dto, StreamObserver<DTOEntities.SurveyTemplateDTO> responseObserver) {
        responseObserver.onNext(surveyRepositoryService.saveTemplate(dto));
        responseObserver.onCompleted();
    }

    @Override
    public void findAllTemplates(Response.Empty e, StreamObserver<DTOEntities.SurveyTemplateDTOList> responseObserver) {
        responseObserver.onNext(surveyRepositoryService.findAllTemplates());
        responseObserver.onCompleted();
    }

    @Override
    public void findBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.SurveyDTOList> responseObserver) {
        responseObserver.onNext(surveyRepositoryService.findBySessionId(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAllBySessionId(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        surveyRepositoryService.deleteAllBySessionId(request);
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void closeSurvey(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSurveyDTO> responseObserver) {
        responseObserver.onNext(surveyRepositoryService.closeSurvey(request));
        responseObserver.onCompleted();
    }

}
