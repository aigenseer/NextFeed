package com.nextfeed.service.repository.survey;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.ExistsSurveyAnswerByParticipantRequest;
import com.nextfeed.library.core.proto.repository.SurveyRepositoryServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;

@EntityScan("com.nextfeed.library.core.entity.survey")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RequiredArgsConstructor
@GrpcService
public class SurveyRepositoryGRPCService extends SurveyRepositoryServiceGrpc.SurveyRepositoryServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(SurveyRepositoryGRPCService.class, args);
    }

    private final SurveyDBService surveyDBService;
    private final SurveyAnswerDBService surveyAnswerDBService;
    private final SurveyTemplateDBService surveyTemplateDBService;

    @Override
    public void saveSurvey(DTOEntities.SurveyDTO dto, StreamObserver<DTOEntities.SurveyDTO> responseObserver) {
        var e = DTO2EntityUtils.dto2Survey(dto);
        e =  surveyDBService.save(e);
        responseObserver.onNext(Entity2DTOUtils.survey2DTO(e));
        responseObserver.onCompleted();
    }

    @Override
    public void findSurveyById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSurveyDTO> responseObserver) {
        var e = surveyDBService.findById(request.getId());
        responseObserver.onNext(DTOEntities.OptionalSurveyDTO.newBuilder().setSurvey(Entity2DTOUtils.survey2DTO(e)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void saveAnswer(DTOEntities.SurveyAnswerDTO dto, StreamObserver<DTOEntities.SurveyAnswerDTO> responseObserver) {
        var e = DTO2EntityUtils.dto2SurveyAnswer(dto);
        e = surveyAnswerDBService.save(e);
        responseObserver.onNext(Entity2DTOUtils.surveyAnswer2DTO(e));
        responseObserver.onCompleted();
    }

    @Override
    public void existsSurveyAnswerByParticipant(ExistsSurveyAnswerByParticipantRequest request, StreamObserver<Response.BooleanResponse> responseObserver) {
        var b = surveyAnswerDBService.existsSurveyAnswerByParticipant(request.getParticipantId(), request.getSurveyId());
        responseObserver.onNext(DTOResponseUtils.createBooleanResponse(b));
        responseObserver.onCompleted();
    }

    @Override
    public void saveTemplate(DTOEntities.SurveyTemplateDTO dto, StreamObserver<DTOEntities.SurveyTemplateDTO> responseObserver) {
        var e = DTO2EntityUtils.dto2SurveyTemplate(dto);
        e = surveyTemplateDBService.save(e);
        responseObserver.onNext(Entity2DTOUtils.surveyTemplate2DTO(e));
        responseObserver.onCompleted();
    }

    @Override
    public void findAllTemplates(Response.Empty e, StreamObserver<DTOEntities.SurveyTemplateDTOList> responseObserver) {
        var list = surveyTemplateDBService.findAll();
        responseObserver.onNext(DTOListUtils.surveyTemplateList2DTO(list));
        responseObserver.onCompleted();
    }

    @Override
    public void findBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.SurveyDTOList> responseObserver) {
        var list = surveyDBService.getRepo().findBySessionId(request.getId());
        responseObserver.onNext(DTOListUtils.surveyList2DTO(list));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAllBySessionId(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        surveyDBService.getRepo().deleteAllBySessionId(request.getId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void closeSurvey(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSurveyDTO> responseObserver) {
        DTOEntities.SurveyDTO dto = null;
        var o = surveyDBService.getRepo().findById(request.getId());
        if(o.isPresent()){
            var e = o.get();
            e.setTimestamp(new Date().getTime());
            e = surveyDBService.save(e);
            dto = Entity2DTOUtils.survey2DTO(e);
        }
        responseObserver.onNext(DTOEntities.OptionalSurveyDTO.newBuilder().setSurvey(dto).build());
        responseObserver.onCompleted();
    }

}
