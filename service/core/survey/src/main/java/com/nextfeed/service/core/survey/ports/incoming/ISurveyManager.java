package com.nextfeed.service.core.survey.ports.incoming;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.valueobject.survey.SurveyValueList;
import com.nextfeed.library.core.valueobject.surveytemplate.OptionalSurveyTemplateValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValueList;

public interface ISurveyManager {

    SurveyTemplateValue createSurvey(Integer sessionId, SurveyTemplateValue template);

    SurveyValueList getSurveysBySessionId(Integer sessionId);

    void addAnswerToSurvey(int sessionId, int surveyId, int participantId, String answer);

    SurveyTemplateValue saveTemplate(SurveyTemplate surveyTemplate);

    OptionalSurveyTemplateValue findTemplateById(Requests.IDRequest request);

    SurveyTemplateValueList findAllTemplates();

}
