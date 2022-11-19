package com.nextfeed.library.core.valueobject.session;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Builder
public class OptionalSessionValue {

    private final Optional<SessionValue> source;

    @Builder(builderMethodName = "dtoBuilder")
    public static OptionalSessionValue newValueDTO(DTOEntities.OptionalSessionDTO optionalDTO) {
        return new OptionalSessionValue(optionalDTO.isInitialized()? Optional.of(SessionValue.dtoBuilder().dto(optionalDTO.getSession()).build()): Optional.empty());
    }

    public Optional<SessionValue> getOptional() {
        return source.isPresent()? Optional.of(get()): Optional.empty();
    }

    public SessionValue get() {
        return source.get();
    }

    public DTOEntities.OptionalSessionDTO getOptionalDTO() {
        var builder = DTOEntities.OptionalSessionDTO.newBuilder();
        if(isPresent()) builder.setSession(get().getDTO());
        return builder.build();
    }

    public boolean isPresent(){
        return source.isPresent();
    }
}
