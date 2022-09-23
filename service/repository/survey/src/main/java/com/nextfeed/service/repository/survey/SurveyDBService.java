package com.nextfeed.service.repository.survey;


import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.manager.repository.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class SurveyDBService extends AbstractService<Survey, SurveyRepository> {
    public SurveyDBService(SurveyRepository surveyRepository) {
        super(surveyRepository);
    }
}
