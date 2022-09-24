package com.nextfeed.service.repository.question;

import com.nextfeed.library.core.entity.question.VoterEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoterRepository extends CrudRepository<VoterEntity, Integer> {

    @Query("SELECT s FROM VoterEntity s WHERE s.question_id = ?1")
    List<VoterEntity> findByQuestionId(Integer question_id);

    @Query("DELETE FROM VoterEntity s WHERE s.question_id = ?1")
    void deleteAllBySessionId(Integer question_id);

}
