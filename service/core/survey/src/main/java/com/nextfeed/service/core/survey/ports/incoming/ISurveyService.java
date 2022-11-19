package com.nextfeed.service.core.survey.ports.incoming;

import com.nextfeed.library.core.valueobject.survey.SurveyValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;

public interface ISurveyService {
    void onCreateByPresenter(int sessionId, SurveyValue surveyDTO);
    void onCreateByParticipant(int sessionId, int surveyId, SurveyTemplateValue template);
    void onClose(int sessionId, int surveyId);
    void onUpdate(int sessionId, SurveyValue surveyDTO);
    void onResult(int sessionId, SurveyValue surveyDTO);
}
