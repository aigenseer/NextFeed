package com.nextfeed.service.core.survey.core;

import com.nextfeed.library.core.grpc.service.repository.SurveyRepositoryServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SurveyTemplateManager {

    private final SurveyRepositoryServiceClient surveyRepositoryServiceClient;

    public DTOEntities.SurveyTemplateDTO createTemplate(DTOEntities.SurveyTemplateDTO template){
        return surveyRepositoryServiceClient.saveTemplate(template);
    }

    public DTOEntities.SurveyTemplateDTOList getAllTemplates(){
        return surveyRepositoryServiceClient.findAllTemplates();
    }

    public Optional<DTOEntities.SurveyTemplateDTO> getTemplateById(int templateId){
        return surveyRepositoryServiceClient.findTemplateById(templateId);
    }



}
