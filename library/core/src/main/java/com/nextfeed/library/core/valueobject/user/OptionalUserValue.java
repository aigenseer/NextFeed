package com.nextfeed.library.core.valueobject.user;

import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class OptionalUserValue {

    private final Optional<UserValue> entity;

    public Optional<UserValue> getOptional() {
        return entity.isPresent()? entity: Optional.empty();
    }

    public static OptionalUserValue createByEntity(Optional<User> optionalEntity) {
        return new OptionalUserValue(optionalEntity.isPresent()? Optional.of(UserValue.createByEntity(optionalEntity.get())) : Optional.empty());
    }

    public static OptionalUserValue createByDTO(DTOEntities.OptionalUserDTO dto) {
        return new OptionalUserValue(dto.isInitialized()? Optional.of(UserValue.createByDTO(dto.getUserDTO())) : Optional.empty());
    }

    public UserValue get() {
        return entity.get();
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
