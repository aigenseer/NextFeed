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

    public UserValue get() {
        return UserValue.builder().entity(entity.get()).build();
    }

    public DTOEntities.OptionalUserDTO getOptionalDTO() {
        return DTOEntities.OptionalUserDTO.newBuilder().setUserDTO(isPresent()? get().getDTO(): null).build();
    }

    public boolean isPresent(){
        return entity.isPresent();
    }
}
