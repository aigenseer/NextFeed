package com.nextfeed.service.core.survey.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;

public interface ISurveyService {
    void onCreateByPresenter(int sessionId, DTOEntities.SurveyDTO surveyDTO);
    void onCreateByParticipant(int sessionId, int surveyId, DTOEntities.SurveyTemplateDTO template);
    void onClose(int sessionId, int surveyId);
    void onUpdate(int sessionId, DTOEntities.SurveyDTO surveyDTO);
    void onResult(int sessionId, DTOEntities.SurveyDTO surveyDTO);
}
