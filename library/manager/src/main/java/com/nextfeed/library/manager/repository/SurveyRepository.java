package com.nextfeed.library.manager.repository;

import com.nextfeed.library.core.entity.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey,Integer> {
}
