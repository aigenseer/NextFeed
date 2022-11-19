package com.nextfeed.library.core.valueobject.survey;

import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Builder
public class OptionalSurveyValue {

    private final Optional<Survey> optionalEntity;

    public Optional<SurveyValue> getOptional() {
        return optionalEntity.isPresent()? Optional.of(get()): Optional.empty();
    }

    public SurveyValue get() {
        return SurveyValue.Builder().entity(optionalEntity.get()).build();
    }

    public DTOEntities.OptionalSurveyDTO getOptionalDTO() {
        var builder = DTOEntities.OptionalSurveyDTO.newBuilder();
        if(isPresent()) builder.setSurvey(get().getDTO());
        return builder.build();
    }

    public boolean isPresent(){
        return optionalEntity.isPresent();
    }
}
