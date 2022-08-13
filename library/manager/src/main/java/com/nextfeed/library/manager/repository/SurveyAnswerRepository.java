package com.nextfeed.library.manager.repository;

import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer,Integer> {
}
