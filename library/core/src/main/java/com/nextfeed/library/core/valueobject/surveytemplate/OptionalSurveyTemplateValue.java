package com.nextfeed.library.core.valueobject.surveytemplate;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Builder
public class OptionalSurveyTemplateValue {

    private final Optional<SurveyTemplate> entity;

    @Builder(builderMethodName = "DTOBuilder")
    public static OptionalSurveyTemplateValue newValue(DTOEntities.OptionalSurveyTemplateDTO dto) {
        return new OptionalSurveyTemplateValue(dto.isInitialized()? Optional.of(SurveyTemplateValue.dtoToEntity(dto.getSurveyTemplate())): Optional.empty());
    }

    public Optional<SurveyTemplateValue> getOptional() {
        return entity.isPresent()? Optional.of(get()): Optional.empty();
    }

    public SurveyTemplateValue get() {
        return SurveyTemplateValue.builder().entity(entity.get()).build();
    }

    public DTOEntities.OptionalSurveyTemplateDTO getOptionalDTO() {
        return DTOEntities.OptionalSurveyTemplateDTO.newBuilder().setSurveyTemplate(isPresent()? get().getDTO(): null).build();
    }

    public boolean isPresent(){
        return entity.isPresent();
    }
}
