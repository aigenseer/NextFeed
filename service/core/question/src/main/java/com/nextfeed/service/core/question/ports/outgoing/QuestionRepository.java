package com.nextfeed.service.core.question.ports.outgoing;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<QuestionEntity, Integer> {

    @Query("SELECT s FROM QuestionEntity s WHERE s.session_id = ?1")
    List<QuestionEntity> findBySessionId(Integer session_id);

    @Query("DELETE FROM QuestionEntity s WHERE s.session_id = ?1")
    List<QuestionEntity> deleteAllBySessionId(Integer session_id);

}
