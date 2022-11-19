package com.nextfeed.library.core.valueobject.participant;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class OptionalParticipantValue {

    private final Optional<Participant> entity;

    public static OptionalParticipantValue createByDTO(DTOEntities.OptionalParticipantDTO dto) {
        return new OptionalParticipantValue(dto.isInitialized()? Optional.of(ParticipantValue.dtoToEntity(dto.getParticipant())): Optional.empty());
    }

    public static OptionalParticipantValue createByOptionalEntity(Optional<Participant> entity) {
        return new OptionalParticipantValue(entity);
    }

    public Optional<ParticipantValue> getOptional() {
        return entity.isPresent()? Optional.of(get()): Optional.empty();
    }

    public ParticipantValue get() {
        return ParticipantValue.createByEntity(entity.get());
    }

    public DTOEntities.OptionalParticipantDTO getOptionalDTO() {
        var builder = DTOEntities.OptionalParticipantDTO.newBuilder();
        if(isPresent()) builder.setParticipant(get().getDTO());
        return builder.build();
    }

    public boolean isPresent(){
        return entity.isPresent();
    }
}
