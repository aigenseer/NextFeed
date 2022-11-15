package com.nextfeed.service.supporting.management.user.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;

import java.util.Date;
import java.util.Optional;

public interface IUserManager {

    DTOEntities.UserDTO createUser(String mailAddress, String name, String pw);
    Optional<DTOEntities.UserDTO> getUserById(Integer id);
    Optional<DTOEntities.UserDTO> getUserByMailAddress(String mailAddress);
    boolean validatePasswordByMailAddress(String mailAddress, String pw);
}
