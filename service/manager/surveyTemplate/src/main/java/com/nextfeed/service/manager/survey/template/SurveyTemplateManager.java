package com.nextfeed.service.manager.survey.template;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.manager.repository.service.SurveyTemplateDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SurveyTemplateManager {

    private final SurveyTemplateDBService surveyTemplateDBService;

    public SurveyTemplate createTemplate(SurveyTemplate template){
        return surveyTemplateDBService.save(template);
    }

    public Collection<SurveyTemplate> getAllTemplates(){
        return surveyTemplateDBService.findAll();
    }

    public SurveyTemplate getTemplateById(int templateId){
        return surveyTemplateDBService.findById(templateId);
    }



}
