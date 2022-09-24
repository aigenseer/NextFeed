package com.nextfeed.service.repository.survey;

import com.nextfeed.library.core.entity.survey.Survey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends CrudRepository<Survey,Integer> {

    @Query("SELECT s FROM Survey s WHERE s.session_id = ?1")
    List<Survey> findBySessionId(Integer session_id);

    @Query("DELETE FROM Survey s WHERE s.session_id = ?1")
    void deleteAllBySessionId(Integer session_id);

}
