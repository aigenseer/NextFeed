package com.nextfeed.library.manager.repository;


import com.nextfeed.library.core.entity.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends CrudRepository<Session, Integer> {
    List<Session> findByClosed(long closed);

    @Query("SELECT s FROM Session s WHERE s.closed > 0")
    List<Session> findAllClosed();

}
