package com.nextfeed.library.core.valueobject.user;

import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Builder
public class OptionalUserValue {

    private final Optional<User> entity;

    public Optional<UserValue> getOptional() {
        return entity.isPresent()? Optional.of(get()): Optional.empty();
    }

    @Builder(builderMethodName = "dtoBuilder")
    public static OptionalUserValue newOptionalUserValue(DTOEntities.OptionalUserDTO dto) {
        return new OptionalUserValue(dto.isInitialized()? Optional.of(UserValue.dtoBuilder().dto(dto.getUserDTO()).build().getEntity()) : Optional.empty());
    }

    public UserValue get() {
        return UserValue.Builder().entity(entity.get()).build();
    }

    public DTOEntities.OptionalUserDTO getOptionalDTO() {
        var builder = DTOEntities.OptionalUserDTO.newBuilder();
        if(isPresent()) builder.setUserDTO(get().getDTO());
        return builder.build();
    }

    public boolean isPresent(){
        return entity.isPresent();
    }
}
