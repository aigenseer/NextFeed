package com.nextfeed.library.core.valueobject.survey;

import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.IValueObject;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;

import java.util.HashSet;

public class SurveyValue implements IValueObject<Survey, DTOEntities.SurveyDTO> {

    private final Survey entity;

    SurveyValue(DTOEntities.SurveyDTO dto){
        entity = dtoToEntity(dto);
    }

    SurveyValue(Survey entity){
        this.entity = entity;
    }

    public static SurveyValue createByDTO(DTOEntities.SurveyDTO dto) {
        return new SurveyValue(dto);
    }

    public static SurveyValue createByEntity(Survey entity) {
        return new SurveyValue(entity);
    }

    private Survey dtoToEntity(DTOEntities.SurveyDTO dto){
        return Survey.builder()
                .id(dto.getId())
                .surveyAnswers(new HashSet(dto.getSurveyAnswersList().stream().map(this::dto2SurveyAnswer).toList()))
                .template(SurveyTemplateValue.createByDTO(dto.getTemplate()).getEntity())
                .timestamp(dto.getTimestamp())
                .session_id(dto.getSessionId())
                .build();
    }

    private SurveyAnswer dto2SurveyAnswer(DTOEntities.SurveyAnswerDTO dto){
        return SurveyAnswer.builder()
                .id(dto.getId())
                .value(dto.getValue())
                .survey_id(dto.getSurveyId())
                .participantId(dto.getParticipantId())
                .build();
    }

    public DTOEntities.SurveyDTO getDTO(){
        var builder = DTOEntities.SurveyDTO.newBuilder()
                .setId(entity.getId())
                .setTemplate(SurveyTemplateValue.createByEntity(entity.getTemplate()).getDTO())
                .setTimestamp(entity.getTimestamp())
                .setSessionId(entity.getSession_id());
        var list = entity.getSurveyAnswers().stream().map(this::surveyAnswer2DTO).toList();
        builder.addAllSurveyAnswers(list);
        return builder.build();
    }

    private DTOEntities.SurveyAnswerDTO surveyAnswer2DTO(SurveyAnswer e){
        if (e == null) return null;
        return DTOEntities.SurveyAnswerDTO.newBuilder()
                .setId(e.getId())
                .setValue(e.getValue())
                .setSurveyId(e.getSurvey_id())
                .setParticipantId(e.getParticipantId())
                .build();
    }

    public Survey getEntity(){
        return entity;
    }

}
