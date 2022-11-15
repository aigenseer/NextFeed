package com.nextfeed.service.supporting.management.user.core.user.db;

import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.service.supporting.management.user.ports.incoming.IUserRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserRepositoryService implements IUserRepositoryService {

    private final UserDBService userDBService;

    @Override
    public User save(User toAdd) {
        return userDBService.save(toAdd);
    }

    @Override
    public User findById(int id) {
        return userDBService.findById(id);
    }

    @Override
    public Optional<User> getUsersByMailAddress(String mailAddress) {
        return userDBService.getUsersByMailAddress(mailAddress);
    }

}
