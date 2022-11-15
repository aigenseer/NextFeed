package com.nextfeed.service.core.survey.ports.outgoing;

import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyAnswerRepository extends CrudRepository<SurveyAnswer,Integer> {

    @Query("SELECT s.id FROM SurveyAnswer s WHERE s.participantId = ?1 AND s.survey_id = ?2")
    List<Integer> searchAnswerId(Integer participant_id, Integer survey_id);

}
