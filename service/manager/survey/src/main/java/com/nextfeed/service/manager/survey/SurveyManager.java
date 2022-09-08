package com.nextfeed.service.manager.survey;

import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.manager.SessionManagerService;
import com.nextfeed.library.core.service.manager.dto.survey.SurveyDTO;
import com.nextfeed.library.core.service.socket.SurveySocketServices;
import com.nextfeed.library.manager.repository.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SurveyManager {

    //todo: muss noch gemacht werden
    private final SurveySocketServices surveySocketServices;
    private final SessionManagerService sessionManagerService;
    private final SurveyDBService surveyDBService;
    private final SurveyAnswerDBService surveyAnswerDBService;

    public SurveyDTO surveyDTOMapping(Survey survey){
        return SurveyDTO.builder()
                .id(survey.getId())
                .answers(survey.getAnswers())
                .template(survey.getTemplate())
                .timestamp(survey.getTimestamp())
                .build();
    }

    public List<SurveyDTO> getSurveysBySessionId(Integer sessionId){
        return sessionManagerService.getSessionById(sessionId).getSurveys().stream().map(this::surveyDTOMapping).toList();
    }

    public SurveyTemplate createSurvey(Integer sessionId, SurveyTemplate template){
        Survey survey = Survey.builder().template(template).session_id(sessionId).build();
        updateSurvey(survey);

        //todo: muss noch gemacht werden
        surveySocketServices.onCreateByPresenter(sessionId, surveyDTOMapping(survey));
        surveySocketServices.onCreateByParticipant(sessionId, survey.getId(), template);

        //start Thread to publish survey after a given amount of time
        //todo: muss noch gemacht werden
        new SurveyTimer(sessionId, survey.getId(), surveySocketServices, this).start();

        return template;
    }

    @Caching(evict = {
            @CacheEvict(value = "existsSurveyById", key = "#survey.id")
    })
    public void updateSurvey(Survey survey){
        surveyDBService.save(survey);
    }


    public void addAnswerToSurvey(int sessionId, int surveyId, int participantId, String answer){
        Survey survey = getSurveyById(surveyId);
        if(survey != null){
            this.addAnswerToSurvey(surveyId, participantId, answer);
            surveySocketServices.onUpdate(sessionId, surveyDTOMapping(getSurveyById(surveyId)));
        }
    }

    private void addAnswerToSurvey(int surveyId, int participantId, String answerValue){
        if(!surveyAnswerDBService.existsSurveyAnswerByParticipant(participantId, surveyId)){
            SurveyAnswer surveyAnswer = SurveyAnswer.builder()
                    .survey_id(surveyId)
                    .participantId(participantId)
                    .value(answerValue)
                    .build();
            surveyAnswerDBService.save(surveyAnswer);
        }
    }

    public Survey getSurveyById(int id){
        return surveyDBService.findById(id);
    }

    @Cacheable(value = "existsSurveyById", key = "#id")
    public boolean existsSurveyById(int id){
        return surveyDBService.existsById(id);
    }

}
