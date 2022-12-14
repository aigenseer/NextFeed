package com.nextfeed.library.manager.repository;


import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant,Integer> {

    @Query("SELECT s FROM Participant s WHERE s.session_id = ?1")
    List<Participant> findBySessionId(Integer session_id);
}
