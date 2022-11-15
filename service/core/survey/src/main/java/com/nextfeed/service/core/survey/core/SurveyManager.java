package com.nextfeed.service.core.survey.core;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.service.socket.SurveySocketServices;
import com.nextfeed.service.core.survey.core.db.SurveyRepositoryService;
import com.nextfeed.service.core.survey.ports.incoming.ISurveyManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SurveyManager implements ISurveyManager {

    private final SurveySocketServices surveySocketServices;
    @Getter
    private final SurveyRepositoryService surveyRepositoryService;

    public DTOEntities.SurveyDTOList getSurveysBySessionId(Integer sessionId){
        return surveyRepositoryService.findBySessionId(sessionId);
    }

    public DTOEntities.SurveyTemplateDTO createSurvey(Integer sessionId, DTOEntities.SurveyTemplateDTO template){
        DTOEntities.SurveyDTO dto = DTOEntities.SurveyDTO.newBuilder().setTemplate(template).setSessionId(sessionId).build();
        dto = surveyRepositoryService.saveSurvey(dto);

        //todo: muss noch gemacht werden
        surveySocketServices.onCreateByPresenter(sessionId, dto);
        surveySocketServices.onCreateByParticipant(sessionId, dto.getId(), template);

        //start Thread to publish survey after a given amount of time
        //todo: muss noch gemacht werden
        new SurveyTimer(sessionId, dto.getId(), surveySocketServices, this).start();

        return template;
    }

    public void addAnswerToSurvey(int sessionId, int surveyId, int participantId, String answer){
        var survey = getSurveyById(surveyId);
        if(survey.isInitialized()){
            this.addAnswerToSurvey(surveyId, participantId, answer);
            survey = getSurveyById(surveyId);
            surveySocketServices.onUpdate(sessionId, survey.getSurvey());
        }
    }

    private void addAnswerToSurvey(int surveyId, int participantId, String answerValue){
        if(!surveyRepositoryService.existsSurveyAnswerByParticipant(participantId, surveyId).getResult()){
            DTOEntities.SurveyAnswerDTO dto = DTOEntities.SurveyAnswerDTO.newBuilder()
                    .setSurveyId(surveyId)
                    .setParticipantId(participantId)
                    .setValue(answerValue)
                    .build();
            surveyRepositoryService.saveAnswer(dto);
        }
    }

    public DTOEntities.OptionalSurveyDTO getSurveyById(int id){
        return surveyRepositoryService.findSurveyById(id);
    }

}
