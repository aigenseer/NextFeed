package com.nextfeed.service.core.survey.core;

import com.nextfeed.library.core.service.socket.SurveySocketServices;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class SurveyTimer extends Thread{
    private final int sessionId;
    private final int surveyId;
    private final SurveySocketServices surveySocketServices;
    private final SurveyManager surveyManager;

    @Override
    public void run() {
        try {
            var survey = surveyManager.getSurveyById(surveyId);
            if(survey.isInitialized()){
                sleep(survey.getSurvey().getTemplate().getDuration() * 1000L);
                survey = surveyManager.getSurveyRepositoryService().closeSurvey(survey.getSurvey().getId());
                surveySocketServices.onClose(sessionId, survey.getSurvey().getId());
                surveySocketServices.onResult(sessionId, survey.getSurvey());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
