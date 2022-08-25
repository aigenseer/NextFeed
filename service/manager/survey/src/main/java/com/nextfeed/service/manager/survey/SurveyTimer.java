package com.nextfeed.service.manager.survey;


import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.service.socket.SurveySocketServices;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
public class SurveyTimer extends Thread{
    private final int sessionId;
    private final int surveyId;
    //todo: muss noch gemacht werden
    private final SurveySocketServices surveySocketServices;
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
            surveySocketServices.onClose(sessionId, survey.getId());
            surveySocketServices.onResult(sessionId, survey);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
