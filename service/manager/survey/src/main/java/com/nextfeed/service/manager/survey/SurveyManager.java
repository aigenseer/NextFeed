package com.nextfeed.service.manager.survey;

import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.SessionManagerService;
import com.nextfeed.library.manager.repository.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
@RequiredArgsConstructor
public class SurveyManager {

    //todo: muss noch gemacht werden
//    private final SurveyService surveyService;
    private final SessionManagerService sessionManagerService;
    private final SurveyDBService surveyDBService;
    private final SurveyAnswerDBService surveyAnswerDBService;

    public Collection<Survey> getSurveysBySessionId(Integer sessionId){
        return sessionManagerService.getSessionById(sessionId).getSurveys();
    }

    public SurveyTemplate createSurvey(Integer sessionId, SurveyTemplate template){
        Survey survey = Survey.builder().template(template).build();
        sessionManagerService.getSessionById(sessionId).getSurveys().add(survey);
        surveyDBService.save(survey);

        //todo: muss noch gemacht werden
//        surveyService.onCreateByAdmin(sessionId, survey);
//        surveyService.onCreateByParticipant(sessionId, survey.getId(), template);

        //start Thread to publish survey after a given amount of time
        //todo: muss noch gemacht werden
//        new SurveyTimer(sessionId, survey.getId(), surveyService, this).start();

        return template;
    }

    public void updateSurvey(Survey survey){
        surveyDBService.save(survey);
    }

    public void addAnswerToSurvey(int sessionId, int surveyId, int participantId, String answer){
        Survey survey = getSurveyById(surveyId);
        if(survey != null){
            this.addAnswerToSurvey(survey, participantId, answer);
            //todo: muss noch gemacht werden
//            surveyService.onUpdate(sessionId, getSurveyById(surveyId));
        }
    }

    private void addAnswerToSurvey(Survey survey, int participantId, String answerValue){
        boolean noneMatch = survey.getSurveyAnswers()
                .stream()
                .noneMatch(a -> a.getParticipantId().equals(participantId));
        if(noneMatch){
            SurveyAnswer surveyAnswer = SurveyAnswer.builder()
                    .survey(survey).participantId(participantId).value(answerValue).build();
            surveyAnswerDBService.save(surveyAnswer);
            survey.getSurveyAnswers().add(surveyAnswer);
            surveyDBService.save(survey);
        }
    }

    @Transactional
    public Survey getSurveyById(int id){
        return surveyDBService.findById(id);
    }


}
