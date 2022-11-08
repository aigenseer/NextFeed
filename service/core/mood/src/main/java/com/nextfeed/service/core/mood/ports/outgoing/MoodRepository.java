package com.nextfeed.service.core.mood.ports.outgoing;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MoodRepository extends CrudRepository<MoodEntity,Integer> {

    @Query("SELECT s FROM MoodEntity s WHERE s.session_id = ?1")
    List<MoodEntity> findBySessionId(Integer session_id);

    @Query("DELETE FROM MoodEntity s WHERE s.session_id = ?1")
    void deleteAllBySessionId(Integer session_id);

}
