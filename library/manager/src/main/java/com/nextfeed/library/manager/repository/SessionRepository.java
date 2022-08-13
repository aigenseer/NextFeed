package com.nextfeed.library.manager.repository;


import com.nextfeed.library.core.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    List<Session> findByClosed(long closed);

    @Query("SELECT s FROM Session s WHERE s.closed > 0")
    List<Session> findAllClosed();

}
