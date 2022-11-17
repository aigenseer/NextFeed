package com.nextfeed.library.core.valueobject.survey;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.entity.question.VoterEntity;
import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import com.nextfeed.library.core.valueobject.IValueObject;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;
import lombok.Builder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class SurveyValue implements IValueObject<Survey, DTOEntities.SurveyDTO> {

    private final Survey entity;
    private final Optional<DTOEntities.SurveyDTO> dto;

    SurveyValue(DTOEntities.SurveyDTO dto){
        entity = dtoToEntity(dto);
        this.dto = Optional.of(dto);
    }

    SurveyValue(Survey entity){
        this.entity = entity;
        this.dto = Optional.empty();
    }

    @Builder(builderMethodName = "dtoBuilder")
    public static SurveyValue newValue(DTOEntities.SurveyDTO dto) {
        return new SurveyValue(dto);
    }

    @Builder(builderMethodName = "builder")
    public static SurveyValue newValue(Survey entity) {
        return new SurveyValue(entity);
    }

    private Survey dtoToEntity(DTOEntities.SurveyDTO dto){
        return Survey.builder()
                .id(dto.getId())
                .surveyAnswers(new HashSet(dto.getSurveyAnswersList().stream().map(DTO2EntityUtils::dto2SurveyAnswer).toList()))
                .template(SurveyTemplateValue.dtoBuilder().dto(dto.getTemplate()).build().getEntity())
                .timestamp(dto.getTimestamp())
                .session_id(dto.getSessionId())
                .build();
    }

    public DTOEntities.SurveyDTO getDTO(){
        var builder = DTOEntities.SurveyDTO.newBuilder()
                .setId(entity.getId())
                .setTemplate(SurveyTemplateValue.builder().entity(entity.getTemplate()).build().getDTO())
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
