package com.nextfeed.service.core.survey.core;

import com.nextfeed.library.core.grpc.service.repository.SurveyRepositoryServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.service.socket.SurveySocketServices;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SurveyManager {

    private final SurveySocketServices surveySocketServices;
    @Getter
    private final SurveyRepositoryServiceClient surveyRepositoryServiceClient;

    public DTOEntities.SurveyDTOList getSurveysBySessionId(Integer sessionId){
        return surveyRepositoryServiceClient.findBySessionId(sessionId);
    }

    public DTOEntities.SurveyTemplateDTO createSurvey(Integer sessionId, DTOEntities.SurveyTemplateDTO template){
        DTOEntities.SurveyDTO dto = DTOEntities.SurveyDTO.newBuilder().setTemplate(template).setSessionId(sessionId).build();
        dto = surveyRepositoryServiceClient.saveSurvey(dto);

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
        if(survey.isPresent()){
            this.addAnswerToSurvey(surveyId, participantId, answer);
            survey = getSurveyById(surveyId);
            surveySocketServices.onUpdate(sessionId, survey.get());
        }
    }

    private void addAnswerToSurvey(int surveyId, int participantId, String answerValue){
        if(!surveyRepositoryServiceClient.existsSurveyAnswerByParticipant(participantId, surveyId)){
            DTOEntities.SurveyAnswerDTO dto = DTOEntities.SurveyAnswerDTO.newBuilder()
                    .setSurveyId(surveyId)
                    .setParticipantId(participantId)
                    .setValue(answerValue)
                    .build();
            surveyRepositoryServiceClient.saveAnswer(dto);
        }
    }

    public Optional<DTOEntities.SurveyDTO> getSurveyById(int id){
        return surveyRepositoryServiceClient.findSurveyById(id);
    }

}
