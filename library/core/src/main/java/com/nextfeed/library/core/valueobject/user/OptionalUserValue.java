package com.nextfeed.library.core.valueobject.user;

import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class OptionalUserValue {

    private final Optional<UserValue> entity;

    public Optional<UserValue> getOptional() {
        return entity.isPresent()? entity: Optional.empty();
    }

    @Builder(builderMethodName = "DTOBuilder")
    public static OptionalUserValue newOptionalUserValue(DTOEntities.OptionalUserDTO dto) {
        return new OptionalUserValue(dto.isInitialized()? Optional.of(UserValue.DTOBuilder().dto(dto.getUserDTO()).build()) : Optional.empty());
    }

    @Builder(builderMethodName = "Builder")
    public static OptionalUserValue newValue(Optional<User> optionalEntity) {
        return new OptionalUserValue(optionalEntity.isPresent()? Optional.of(UserValue.Builder().entity(optionalEntity.get()).build()) : Optional.empty());
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
