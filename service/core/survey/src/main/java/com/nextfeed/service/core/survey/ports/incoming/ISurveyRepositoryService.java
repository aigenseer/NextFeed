package com.nextfeed.service.core.survey.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.ExistsSurveyAnswerByParticipantRequest;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTORequestUtils;

public interface ISurveyRepositoryService {
    DTOEntities.SurveyDTO saveSurvey(DTOEntities.SurveyDTO dto);
    DTOEntities.OptionalSurveyDTO findSurveyById(Requests.IDRequest request);
    DTOEntities.SurveyAnswerDTO saveAnswer(DTOEntities.SurveyAnswerDTO dto);
    Response.BooleanResponse existsSurveyAnswerByParticipant(ExistsSurveyAnswerByParticipantRequest request);
    DTOEntities.SurveyTemplateDTO saveTemplate(DTOEntities.SurveyTemplateDTO dto);
    DTOEntities.SurveyTemplateDTOList findAllTemplates();
    DTOEntities.SurveyDTOList findBySessionId(Requests.IDRequest request);
    void deleteAllBySessionId(Requests.IDRequest request);
    DTOEntities.OptionalSurveyDTO closeSurvey(Requests.IDRequest request);
    DTOEntities.OptionalSurveyTemplateDTO findTemplateById(Requests.IDRequest request);
    DTOEntities.OptionalSurveyTemplateDTO findTemplateById(Integer id);
}
