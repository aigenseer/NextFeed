package com.nextfeed.service.supporting.management.user.core.user;

import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.valueobject.user.OptionalUserValue;
import com.nextfeed.library.core.valueobject.user.UserValue;
import com.nextfeed.service.supporting.management.user.core.user.db.UserDBService;
import com.nextfeed.service.supporting.management.user.ports.incoming.IUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserManager implements IUserManager {

    private final UserDBService userRepositoryService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    private UserValue save(UserValue value) {
        return UserValue.createByEntity(userRepositoryService.save(value.getEntity()));
    }

    private UserValue save(User value) {
        return UserValue.createByEntity(userRepositoryService.save(value));
    }

    private UserValue create(String mailAddress, String name, String pw){
        var user = User.builder().mailAddress(mailAddress).name(name).hashPassword(passwordEncoder.encode(pw)).registrationTime(new Date().getTime()).build();
        return save(UserValue.createByEntity(user));
    }

    public UserValue createUser(String mailAddress, String name, String pw){
        return create(mailAddress, name, pw);
    }

    public OptionalUserValue getUserById(Integer id){
        return OptionalUserValue.createByEntity(userRepositoryService.findById(id));
    }

    public OptionalUserValue getUserByMailAddress(String mailAddress){
        return OptionalUserValue.createByEntity(userRepositoryService.getUsersByMailAddress(mailAddress));
    }

    public boolean validatePasswordByMailAddress(String mailAddress, String pw){
        var user = getUserByMailAddress(mailAddress);
        if(user.isPresent()){
            return passwordEncoder.matches(pw, user.get().getEntity().getHashPassword());
        }
        return false;
    }



}
