package com.nextfeed.service.repository.survey;


import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.manager.repository.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class SurveyTemplateDBService extends AbstractService<SurveyTemplate, SurveyTemplateRepository> {
    public SurveyTemplateDBService(SurveyTemplateRepository surveyTemplateRepository) {
        super(surveyTemplateRepository);
    }

}
