package com.nextfeed.library.manager.repository.service;


import com.nextfeed.library.core.entity.User;
import com.nextfeed.library.manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDBService extends AbstractService<User, UserRepository> {
    public UserDBService(UserRepository userRepository) {
        super(userRepository);
    }

    public Optional<User> getUsersByMailAddress(String mailAddress){
        return repo.findAllMailAddress(mailAddress).stream().findFirst();
    }
}
