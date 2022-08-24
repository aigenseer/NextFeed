package com.nextfeed.service.manager.survey;


import com.nextfeed.library.core.entity.survey.Survey;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
public class SurveyTimer extends Thread{
    private final int sessionId;
    private final int surveyId;
    //todo: muss noch gemacht werden
//    private final SurveyService surveyService;
    private final SurveyManager surveyManager;

    @Override
    public void run() {
        try {
            Survey survey = surveyManager.getSurveyById(surveyId);
            sleep(survey.getTemplate().getDuration() * 1000L);
            survey = surveyManager.getSurveyById(surveyId);
            survey.setTimestamp(new Date().getTime());
            surveyManager.updateSurvey(survey);
            //todo: muss noch gemacht werden
//            surveyService.onClose(sessionId, survey.getId());
//            surveyService.onResult(sessionId, survey);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
