package com.nextfeed.service.core.survey.core.db;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.ExistsSurveyAnswerByParticipantRequest;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.*;
import com.nextfeed.service.core.survey.ports.incoming.ISurveyRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class SurveyRepositoryService implements ISurveyRepositoryService {

    private final SurveyDBService surveyDBService;
    private final SurveyAnswerDBService surveyAnswerDBService;
    private final SurveyTemplateDBService surveyTemplateDBService;

    public DTOEntities.SurveyDTO saveSurvey(DTOEntities.SurveyDTO dto) {
        var e = DTO2EntityUtils.dto2Survey(dto);
        e =  surveyDBService.save(e);
        return Entity2DTOUtils.survey2DTO(e);
    }

    public DTOEntities.OptionalSurveyDTO findSurveyById(Requests.IDRequest request) {
        var e = surveyDBService.findById(request.getId());
        return DTOEntities.OptionalSurveyDTO.newBuilder().setSurvey(Entity2DTOUtils.survey2DTO(e)).build();
    }

    public DTOEntities.OptionalSurveyDTO findSurveyById(Integer id) {
        return findSurveyById(DTORequestUtils.createIDRequest(id));
    }

    public DTOEntities.SurveyAnswerDTO saveAnswer(DTOEntities.SurveyAnswerDTO dto) {
        var e = DTO2EntityUtils.dto2SurveyAnswer(dto);
        e = surveyAnswerDBService.save(e);
        return Entity2DTOUtils.surveyAnswer2DTO(e);
    }

    public Response.BooleanResponse existsSurveyAnswerByParticipant(ExistsSurveyAnswerByParticipantRequest request) {
        var b = surveyAnswerDBService.existsSurveyAnswerByParticipant(request.getParticipantId(), request.getSurveyId());
        return DTOResponseUtils.createBooleanResponse(b);
    }

    public Response.BooleanResponse existsSurveyAnswerByParticipant(Integer participantId, Integer surveyId) {
        return existsSurveyAnswerByParticipant(ExistsSurveyAnswerByParticipantRequest.newBuilder().setParticipantId(participantId).setSurveyId(surveyId).build());
    }

    public DTOEntities.SurveyTemplateDTO saveTemplate(DTOEntities.SurveyTemplateDTO dto) {
        var e = DTO2EntityUtils.dto2SurveyTemplate(dto);
        e = surveyTemplateDBService.save(e);
        return Entity2DTOUtils.surveyTemplate2DTO(e);
    }

    public DTOEntities.SurveyTemplateDTOList findAllTemplates() {
        var list = surveyTemplateDBService.findAll();
        return DTOListUtils.surveyTemplateList2DTO(list);
    }

    public DTOEntities.SurveyDTOList findBySessionId(Requests.IDRequest request) {
        var list = surveyDBService.getRepo().findBySessionId(request.getId());
        return DTOListUtils.surveyList2DTO(list);
    }

    public DTOEntities.SurveyDTOList findBySessionId(Integer id) {
        return findBySessionId(DTORequestUtils.createIDRequest(id));
    }

    public void deleteAllBySessionId(Requests.IDRequest request) {
        surveyDBService.getRepo().deleteAllBySessionId(request.getId());
    }

    public DTOEntities.OptionalSurveyDTO closeSurvey(Requests.IDRequest request) {
        DTOEntities.SurveyDTO dto = null;
        var o = surveyDBService.getRepo().findById(request.getId());
        if(o.isPresent()){
            var e = o.get();
            e.setTimestamp(new Date().getTime());
            e = surveyDBService.save(e);
            dto = Entity2DTOUtils.survey2DTO(e);
        }
        return DTOEntities.OptionalSurveyDTO.newBuilder().setSurvey(dto).build();
    }

    public DTOEntities.OptionalSurveyTemplateDTO findTemplateById(Requests.IDRequest request) {
        var e = surveyTemplateDBService.findById(request.getId());
        var dto = Entity2DTOUtils.surveyTemplate2DTO(e);
        return DTOEntities.OptionalSurveyTemplateDTO.newBuilder().setSurveyTemplate(dto).build();
    }

    @Override
    public DTOEntities.OptionalSurveyTemplateDTO findTemplateById(Integer id) {
        return findTemplateById(DTORequestUtils.createIDRequest(id));
    }

    public DTOEntities.OptionalSurveyDTO closeSurvey(Integer id){
        return closeSurvey(DTORequestUtils.createIDRequest(id));
    }


}
