package com.nextfeed.library.core.valueobject.question;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.entity.question.VoterEntity;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OptionalQuestionValue {

    private final Optional<QuestionEntity> optionalEntity;
    private final ParticipantValue participantValue;
    private final List<VoterEntity> voterEntityList;

    public Optional<QuestionValue> getOptional() {
        return optionalEntity.isPresent()? Optional.of(get()): Optional.empty();
    }

    public static OptionalQuestionValue createByOptionalEntity(Optional<QuestionEntity> optionalEntity, ParticipantValue participantValue, List<VoterEntity> voterEntityList) {
        return new OptionalQuestionValue(optionalEntity, participantValue, voterEntityList);
    }

    public QuestionValue get() {
        return QuestionValue.createByEntity(optionalEntity.get(), participantValue, voterEntityList);
    }

    public DTOEntities.OptionalQuestionDTO getOptionalDTO() {
        var builder = DTOEntities.OptionalQuestionDTO.newBuilder();
        if(isPresent()) builder.setQuestion(get().getDTO());
        return builder.build();
    }

    public boolean isPresent(){
        return optionalEntity.isPresent();
    }
}
