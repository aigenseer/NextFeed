package com.nextfeed.library.core.grpc.service.repository;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.ExistsSurveyAnswerByParticipantRequest;
import com.nextfeed.library.core.proto.repository.SurveyRepositoryServiceGrpc;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SurveyRepositoryServiceClient {

    @GrpcClient("survey-service")
    private SurveyRepositoryServiceGrpc.SurveyRepositoryServiceBlockingStub rpcService;

    public DTOEntities.SurveyDTO saveSurvey(DTOEntities.SurveyDTO dto) {
        return rpcService.saveSurvey(dto);
    }

    public Optional<DTOEntities.SurveyDTO> findSurveyById(Integer id) {
        return Optional.of(rpcService.findSurveyById(DTORequestUtils.createIDRequest(id)).getSurvey());
    }

    public DTOEntities.SurveyAnswerDTO saveAnswer(DTOEntities.SurveyAnswerDTO dto) {
        return rpcService.saveAnswer(dto);
    }

    public boolean existsSurveyAnswerByParticipant(Integer participantId, Integer surveyId) {
        return rpcService.existsSurveyAnswerByParticipant(ExistsSurveyAnswerByParticipantRequest.newBuilder().setParticipantId(participantId).setSurveyId(surveyId).build()).getResult();
    }

    public DTOEntities.SurveyTemplateDTO saveTemplate(DTOEntities.SurveyTemplateDTO dto) {
        return rpcService.saveTemplate(dto);
    }

    public DTOEntities.SurveyTemplateDTOList findAllTemplates() {
        return rpcService.findAllTemplates(Response.Empty.newBuilder().build());
    }

    public Optional<DTOEntities.SurveyTemplateDTO> findTemplateById(Integer id){
        return Optional.of(rpcService.findTemplateById(DTORequestUtils.createIDRequest(id)).getSurveyTemplate());
    }

    public DTOEntities.SurveyDTOList findBySessionId(Integer id) {
        return rpcService.findBySessionId(DTORequestUtils.createIDRequest(id));
    }

    public void deleteAllBySessionId(Integer id) {
        rpcService.deleteAllBySessionId(DTORequestUtils.createIDRequest(id));
    }

    public Optional<DTOEntities.SurveyDTO> closeSurvey(Integer id){
        return Optional.of(rpcService.closeSurvey(DTORequestUtils.createIDRequest(id)).getSurvey());
    }


}
