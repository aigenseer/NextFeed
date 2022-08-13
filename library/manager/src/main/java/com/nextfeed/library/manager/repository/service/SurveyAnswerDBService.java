package com.nextfeed.library.manager.repository.service;


import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import com.nextfeed.library.manager.repository.SurveyAnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class SurveyAnswerDBService extends AbstractService<SurveyAnswer, SurveyAnswerRepository> {
    public SurveyAnswerDBService(SurveyAnswerRepository surveyAnswerRepository) {
        super(surveyAnswerRepository);
    }
}
