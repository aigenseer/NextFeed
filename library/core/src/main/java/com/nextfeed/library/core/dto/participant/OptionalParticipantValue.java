package com.nextfeed.library.core.dto.participant;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Builder
public class OptionalParticipantValue {

    private final Optional<Participant> entity;

    public Optional<ParticipantValue> getOptional() {
        return entity.isPresent()? Optional.of(get()): Optional.empty();
    }

    public ParticipantValue get() {
        return ParticipantValue.builder().entity(entity.get()).build();
    }

    public DTOEntities.OptionalParticipantDTO getOptionalParticipantDTO() {
        return DTOEntities.OptionalParticipantDTO.newBuilder().setParticipant(get().getDTO()).build();
    }

    public boolean isPresent(){
        return entity.isPresent();
    }
}
