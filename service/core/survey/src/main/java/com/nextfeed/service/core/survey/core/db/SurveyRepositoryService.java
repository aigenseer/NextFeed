package com.nextfeed.service.core.survey.core.db;

import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.proto.repository.ExistsSurveyAnswerByParticipantRequest;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.valueobject.survey.OptionalSurveyValue;
import com.nextfeed.library.core.valueobject.survey.SurveyValue;
import com.nextfeed.library.core.valueobject.survey.SurveyValueList;
import com.nextfeed.library.core.valueobject.surveytemplate.OptionalSurveyTemplateValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValueList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SurveyRepositoryService {

    private final SurveyDBService surveyDBService;
    private final SurveyAnswerDBService surveyAnswerDBService;
    private final SurveyTemplateDBService surveyTemplateDBService;

    public SurveyValue saveSurvey(Survey entity) {
        return SurveyValue.builder().entity(surveyDBService.save(entity)).build();
    }

    public OptionalSurveyValue findSurveyById(Requests.IDRequest request) {
        var e = surveyDBService.findById(request.getId());
        return OptionalSurveyValue.builder().optionalEntity(e).build();
    }

    public OptionalSurveyValue findSurveyById(Integer id) {
        return findSurveyById(DTORequestUtils.createIDRequest(id));
    }

    public SurveyAnswer saveAnswer(SurveyAnswer surveyAnswer) {
        return surveyAnswerDBService.save(surveyAnswer);
    }

    public boolean existsSurveyAnswerByParticipant(ExistsSurveyAnswerByParticipantRequest request) {
        return surveyAnswerDBService.existsSurveyAnswerByParticipant(request.getParticipantId(), request.getSurveyId());
    }

    public boolean existsSurveyAnswerByParticipant(Integer participantId, Integer surveyId) {
        return existsSurveyAnswerByParticipant(ExistsSurveyAnswerByParticipantRequest.newBuilder().setParticipantId(participantId).setSurveyId(surveyId).build());
    }

    public SurveyTemplateValue saveTemplate(SurveyTemplate surveyTemplate) {
        return SurveyTemplateValue.builder().entity(surveyTemplateDBService.save(surveyTemplate)).build();
    }

    public SurveyTemplateValueList findAllTemplates() {
        return SurveyTemplateValueList.createByEntities(surveyTemplateDBService.findAll());
    }

    public SurveyValueList findBySessionId(Requests.IDRequest request) {
        return SurveyValueList.builder().list(surveyDBService.getRepo().findBySessionId(request.getId())).build();
    }

    public SurveyValueList findBySessionId(Integer id) {
        return findBySessionId(DTORequestUtils.createIDRequest(id));
    }

    public void deleteAllBySessionId(Requests.IDRequest request) {
        surveyDBService.getRepo().deleteAllBySessionId(request.getId());
    }

    public OptionalSurveyValue closeSurvey(Requests.IDRequest request) {
        var o = surveyDBService.getRepo().findById(request.getId());
        if(o.isPresent()){
            var e = o.get();
            e.setTimestamp(new Date().getTime());
            o = Optional.of(surveyDBService.save(e));
        }
        return OptionalSurveyValue.builder().optionalEntity(o).build();
    }

    public OptionalSurveyTemplateValue findTemplateById(Integer id) {
        var e = surveyTemplateDBService.findById(id);
        return OptionalSurveyTemplateValue.builder().entity(e).build();
    }

    public OptionalSurveyValue closeSurvey(Integer id){
        return closeSurvey(DTORequestUtils.createIDRequest(id));
    }


}
