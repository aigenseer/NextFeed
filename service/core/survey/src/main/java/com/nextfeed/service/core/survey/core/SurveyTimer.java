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
            if(survey.isPresent()){
                sleep(survey.get().getTemplate().getDuration() * 1000L);
                survey = surveyManager.getSurveyRepositoryServiceClient().closeSurvey(survey.get().getId());
                surveySocketServices.onClose(sessionId, survey.get().getId());
                surveySocketServices.onResult(sessionId, survey.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
