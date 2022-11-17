package com.nextfeed.service.supporting.management.user.core.user;

import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import com.nextfeed.service.supporting.management.user.core.user.db.UserDBService;
import com.nextfeed.service.supporting.management.user.ports.incoming.IUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserManager implements IUserManager {

    private final UserDBService userRepositoryService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DTOEntities.UserDTO createUser(String mailAddress, String name, String pw){
        var user = User.builder().mailAddress(mailAddress).name(name).hashPassword(passwordEncoder.encode(pw)).registrationTime(new Date().getTime()).build();
        userRepositoryService.save(user);
        return Entity2DTOUtils.user2DTO(user);
    }

    public Optional<DTOEntities.UserDTO> getUserById(Integer id){
        return Optional.ofNullable(Entity2DTOUtils.user2DTO(userRepositoryService.findById(id).orElse(null)));
    }

    public Optional<DTOEntities.UserDTO> getUserByMailAddress(String mailAddress){
        return Optional.ofNullable(Entity2DTOUtils.user2DTO(userRepositoryService.getUsersByMailAddress(mailAddress).orElse(null)));
    }

    public boolean validatePasswordByMailAddress(String mailAddress, String pw){
        var user = getUserByMailAddress(mailAddress);
        if(user.isPresent()){
            return passwordEncoder.matches(pw, user.get().getHashPassword());
        }
        return false;
    }



}
