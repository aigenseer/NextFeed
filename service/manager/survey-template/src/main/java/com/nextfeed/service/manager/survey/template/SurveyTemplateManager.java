package com.nextfeed.service.manager.survey.template;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.repository.SurveyRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SurveyTemplateManager {

    private final SurveyRepositoryService surveyRepositoryService;

    public SurveyTemplate createTemplate(SurveyTemplate template){
        return surveyRepositoryService.save(template);
    }

    public Collection<SurveyTemplate> getAllTemplates(){
        return surveyRepositoryService.findAllTemplates();
    }

    public SurveyTemplate getTemplateById(int templateId){
        return surveyRepositoryService.findTemplateById(templateId);
    }



}
