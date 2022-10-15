package com.nextfeed.service.manager.user;

import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.grpc.service.repository.UserRepositoryServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.service.repository.UserRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final UserRepositoryServiceClient userRepositoryServiceClient;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DTOEntities.UserDTO createUser(String mailAddress, String name, String pw){
        var user = DTOEntities.UserDTO.newBuilder().setMailAddress(mailAddress).setName(name).setHashPassword(passwordEncoder.encode(pw)).setRegistrationTime(new Date().getTime()).build();
        userRepositoryServiceClient.save(user);
        return user;
    }

    public Optional<DTOEntities.UserDTO> getUserById(Integer id){
        return userRepositoryServiceClient.findById(id);
    }

    public Optional<DTOEntities.UserDTO> getUserByMailAddress(String mailAddress){
        return userRepositoryServiceClient.getUsersByMailAddress(mailAddress);
    }

    public boolean validatePasswordByMailAddress(String mailAddress, String pw){
        var user = getUserByMailAddress(mailAddress);
        if(user.isPresent()){
            return passwordEncoder.matches(pw, user.get().getHashPassword());
        }
        return false;
    }



}
