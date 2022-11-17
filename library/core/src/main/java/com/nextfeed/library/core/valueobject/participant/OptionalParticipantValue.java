package com.nextfeed.library.core.valueobject.participant;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Builder
public class OptionalParticipantValue {

    private final Optional<Participant> entity;

    @Builder(builderMethodName = "dtoBuilder")
    public static OptionalParticipantValue newValue(DTOEntities.OptionalParticipantDTO dto) {
        return new OptionalParticipantValue(dto.isInitialized()? Optional.of(ParticipantValue.dtoToEntity(dto.getParticipant())): Optional.empty());
    }

    public Optional<ParticipantValue> getOptional() {
        return entity.isPresent()? Optional.of(get()): Optional.empty();
    }

    public ParticipantValue get() {
        return ParticipantValue.builder().entity(entity.get()).build();
    }

    public DTOEntities.OptionalParticipantDTO getOptionalDTO() {
        return DTOEntities.OptionalParticipantDTO.newBuilder().setParticipant(isPresent()? get().getDTO(): null).build();
    }

    public boolean isPresent(){
        return entity.isPresent();
    }
}
