package com.nextfeed.service.supporting.management.user.core.user.db;


import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.manager.repository.service.AbstractService;
import com.nextfeed.service.supporting.management.user.ports.outgoing.UserRepository;
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
