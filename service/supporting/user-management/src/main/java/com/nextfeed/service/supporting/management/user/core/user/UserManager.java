package com.nextfeed.service.supporting.management.user.core.user;

import com.nextfeed.library.core.valueobject.user.OptionalUserValue;
import com.nextfeed.library.core.valueobject.user.UserValue;
import com.nextfeed.library.core.entity.user.User;
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
        return UserValue.builder().entity(userRepositoryService.save(value.getEntity())).build();
    }

    private UserValue save(User value) {
        return UserValue.builder().entity(userRepositoryService.save(value)).build();
    }

    private UserValue create(String mailAddress, String name, String pw){
        var user = User.builder().mailAddress(mailAddress).name(name).hashPassword(passwordEncoder.encode(pw)).registrationTime(new Date().getTime()).build();
        return save(UserValue.builder().entity(user).build());
    }

    public UserValue createUser(String mailAddress, String name, String pw){
        return createUser(mailAddress, name, pw);
    }

    public OptionalUserValue getUserById(Integer id){
        return OptionalUserValue.builder().entity(userRepositoryService.findById(id)).build();
    }

    public OptionalUserValue getUserByMailAddress(String mailAddress){
        return OptionalUserValue.builder().entity(userRepositoryService.getUsersByMailAddress(mailAddress)).build();
    }

    public boolean validatePasswordByMailAddress(String mailAddress, String pw){
        var user = getUserByMailAddress(mailAddress);
        if(user.isPresent()){
            return passwordEncoder.matches(pw, user.get().getEntity().getHashPassword());
        }
        return false;
    }



}
