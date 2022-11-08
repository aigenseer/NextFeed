package com.nextfeed.service.supporting.management.user.core.participant.db;

import com.nextfeed.library.core.entity.participant.Participant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant,Integer> {

    @Query("SELECT s FROM Participant s WHERE s.session_id = ?1")
    List<Participant> findBySessionId(Integer session_id);

    @Query("DELETE FROM Participant s WHERE s.session_id = ?1")
    void deleteAllBySessionId(Integer session_id);
}
