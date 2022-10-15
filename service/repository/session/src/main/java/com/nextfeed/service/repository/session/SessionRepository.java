package com.nextfeed.service.repository.session;


import com.nextfeed.library.core.entity.session.Session;
import com.nextfeed.library.core.entity.session.SessionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends CrudRepository<SessionEntity, Integer> {
    List<SessionEntity> findByClosed(long closed);

    @Query("SELECT s FROM SessionEntity s WHERE s.closed > 0")
    List<SessionEntity> findAllClosed();

}
