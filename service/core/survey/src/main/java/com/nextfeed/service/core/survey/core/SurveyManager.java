package com.nextfeed.service.core.survey.core;

import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.socket.SurveySocketServices;
import com.nextfeed.library.core.valueobject.survey.OptionalSurveyValue;
import com.nextfeed.library.core.valueobject.survey.SurveyValueList;
import com.nextfeed.library.core.valueobject.surveytemplate.OptionalSurveyTemplateValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValueList;
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

    public SurveyValueList getSurveysBySessionId(Integer sessionId){
        return surveyRepositoryService.findBySessionId(sessionId);
    }

    public SurveyTemplateValue createSurvey(Integer sessionId, SurveyTemplateValue template){
        var survey = Survey.builder().session_id(sessionId).template(template.getEntity()).build();
        var surveyValue = surveyRepositoryService.saveSurvey(survey);

        //todo: muss noch gemacht werden
        surveySocketServices.onCreateByPresenter(sessionId, surveyValue);
        surveySocketServices.onCreateByParticipant(sessionId, surveyValue.getEntity().getId(), template);

        //start Thread to publish survey after a given amount of time
        //todo: muss noch gemacht werden
        new SurveyTimer(sessionId, surveyValue.getEntity().getId(), surveySocketServices, this).start();

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

    @Override
    public SurveyTemplateValue saveTemplate(SurveyTemplate surveyTemplate) {
        return surveyRepositoryService.saveTemplate(surveyTemplate);
    }

    @Override
    public OptionalSurveyTemplateValue findTemplateById(Integer id) {
        return surveyRepositoryService.findTemplateById(id);
    }

    @Override
    public SurveyTemplateValueList findAllTemplates() {
        return surveyRepositoryService.findAllTemplates();
    }

    private void addAnswerToSurvey(int surveyId, int participantId, String answerValue){
        if(!surveyRepositoryService.existsSurveyAnswerByParticipant(participantId, surveyId)){
            var entity = SurveyAnswer.builder()
                    .survey_id(surveyId)
                    .participantId(participantId)
                    .value(answerValue)
                    .build();
            surveyRepositoryService.saveAnswer(entity);
        }
    }

    public OptionalSurveyValue getSurveyById(int id){
        return surveyRepositoryService.findSurveyById(id);
    }

}
