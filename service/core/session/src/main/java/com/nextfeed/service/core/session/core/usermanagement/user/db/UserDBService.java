package com.nextfeed.service.core.session.core.usermanagement.user.db;


import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.manager.repository.service.AbstractService;
import com.nextfeed.service.core.session.ports.outgoing.usermanagement.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDBService extends AbstractService<User, UserRepository> {
    public UserDBService(UserRepository userRepository) {
        super(userRepository);
    }

    public Optional<User> getUsersByMailAddress(String mailAddress) {
        return repo.findAllMailAddress(mailAddress).stream().findFirst();
    }
}
