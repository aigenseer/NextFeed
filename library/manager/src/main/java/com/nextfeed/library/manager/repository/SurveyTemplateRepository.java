package com.nextfeed.library.manager.repository;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyTemplateRepository extends CrudRepository<SurveyTemplate,Integer> {
}
