package com.nextfeed.service.core.survey.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;

public interface ISurveyManager {

    DTOEntities.SurveyTemplateDTO createSurvey(Integer sessionId, DTOEntities.SurveyTemplateDTO template);

    DTOEntities.SurveyDTOList getSurveysBySessionId(Integer sessionId);

    void addAnswerToSurvey(int sessionId, int surveyId, int participantId, String answer);

}
