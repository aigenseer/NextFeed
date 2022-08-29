package com.nextfeed.service.manager.user;

import com.nextfeed.library.core.entity.User;
import com.nextfeed.library.manager.repository.service.UserDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final UserDBService userDBService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(String mailAddress, String name, String pw){
        User user = User.builder().mailAddress(mailAddress).name(name).hashPassword(passwordEncoder.encode(pw)).registrationTime(new Date().getTime()).build();
        userDBService.save(user);
        return user;
    }

    public User getUserById(Integer id){
        return userDBService.findById(id);
    }

    public User getUserByMailAddress(String mailAddress){
        return userDBService.getUsersByMailAddress(mailAddress).orElse(null);
    }

    public boolean validatePasswordByMailAddress(String mailAddress, String pw){
        User user = getUserByMailAddress(mailAddress);
        if(user!= null){
            return passwordEncoder.matches(pw, user.getHashPassword());
        }
        return false;
    }



}
