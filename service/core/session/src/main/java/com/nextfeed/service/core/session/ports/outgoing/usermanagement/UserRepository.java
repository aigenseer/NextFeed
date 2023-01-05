package com.nextfeed.service.core.session.ports.outgoing.usermanagement;

import com.nextfeed.library.core.entity.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    @Query("SELECT s FROM User s WHERE s.mailAddress like ?1")
    List<User> findAllMailAddress(String mailAddress);
}
