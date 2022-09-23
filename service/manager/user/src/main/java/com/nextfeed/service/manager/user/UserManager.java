package com.nextfeed.service.manager.user;

import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.service.repository.UserRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final UserRepositoryService userRepositoryService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(String mailAddress, String name, String pw){
        User user = User.builder().mailAddress(mailAddress).name(name).hashPassword(passwordEncoder.encode(pw)).registrationTime(new Date().getTime()).build();
        userRepositoryService.save(user);
        return user;
    }

    public User getUserById(Integer id){
        return userRepositoryService.findById(id);
    }

    public User getUserByMailAddress(String mailAddress){
        return userRepositoryService.getUsersByMailAddress(mailAddress);
    }

    public boolean validatePasswordByMailAddress(String mailAddress, String pw){
        User user = getUserByMailAddress(mailAddress);
        if(user!= null){
            return passwordEncoder.matches(pw, user.getHashPassword());
        }
        return false;
    }



}
