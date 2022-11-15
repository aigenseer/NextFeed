package com.nextfeed.service.supporting.management.user.ports.incoming;

import com.nextfeed.library.core.entity.user.User;

import java.util.Optional;

public interface IUserRepositoryService {

    User save(User toAdd);
    User findById(int id);
    Optional<User> getUsersByMailAddress(String mailAddress);

}
