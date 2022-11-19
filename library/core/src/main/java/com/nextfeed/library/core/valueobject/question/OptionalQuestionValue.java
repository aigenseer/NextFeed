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
@Builder
public class OptionalQuestionValue {

    private final Optional<QuestionEntity> optionalEntity;
    private final ParticipantValue participantValue;
    private final List<VoterEntity> voterEntityList;

    public Optional<QuestionValue> getOptional() {
        return optionalEntity.isPresent()? Optional.of(get()): Optional.empty();
    }

    public QuestionValue get() {
        return QuestionValue.Builder().entity(optionalEntity.get()).participantValue(participantValue).voterEntityList(voterEntityList).build();
    }

    public DTOEntities.OptionalQuestionDTO getOptionalDTO() {
        return DTOEntities.OptionalQuestionDTO.newBuilder().setQuestion(isPresent()? get().getDTO(): null).build();
    }

    public boolean isPresent(){
        return optionalEntity.isPresent();
    }
}
