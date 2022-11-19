package com.nextfeed.library.core.valueobject.user;

import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.IValueObject;

public class UserValue implements IValueObject<User, DTOEntities.UserDTO> {

    private final User entity;

    UserValue(DTOEntities.UserDTO dto){
        entity = dtoToEntity(dto);
    }

    UserValue(User entity){
        this.entity = entity;
    }

    public static UserValue createByDTO(DTOEntities.UserDTO dto) {
        return new UserValue(dto);
    }

    public static UserValue createByEntity(User entity) {
        return new UserValue(entity);
    }

    private User dtoToEntity(DTOEntities.UserDTO dto){
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .hashPassword(dto.getHashPassword())
                .registrationTime(dto.getRegistrationTime())
                .mailAddress(dto.getMailAddress())
                .build();
    }

    public DTOEntities.UserDTO getDTO(){
        return DTOEntities.UserDTO.newBuilder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setHashPassword(entity.getHashPassword())
                .setRegistrationTime(entity.getRegistrationTime())
                .setMailAddress(entity.getMailAddress())
                .build();
    }

    public User getEntity(){
        return entity;
    }

}
