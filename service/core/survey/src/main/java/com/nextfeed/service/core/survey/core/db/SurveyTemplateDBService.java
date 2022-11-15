package com.nextfeed.service.core.survey.core.db;


import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.manager.repository.service.AbstractService;
import com.nextfeed.service.core.survey.ports.outgoing.SurveyTemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class SurveyTemplateDBService extends AbstractService<SurveyTemplate, SurveyTemplateRepository> {
    public SurveyTemplateDBService(SurveyTemplateRepository surveyTemplateRepository) {
        super(surveyTemplateRepository);
    }

}
