package com.nextfeed.service.repository.survey;

import com.nextfeed.library.core.entity.survey.Survey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends CrudRepository<Survey,Integer> {
}
