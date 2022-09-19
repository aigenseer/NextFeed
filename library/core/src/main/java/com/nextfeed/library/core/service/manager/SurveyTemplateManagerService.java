package com.nextfeed.library.core.service.manager;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@FeignClient(name = "survey-template-manager-service-x", url = "${nextfeed.service.survey-template-manager-service.domain}:${nextfeed.service.survey-template-manager-service.port}", path = "/api/survey-template-manager")
public interface SurveyTemplateManagerService {

    @RequestMapping(value = "/v1/survey/template/create", method = RequestMethod.POST)
    public SurveyTemplate createTemplate(@RequestBody SurveyTemplate template);

    @RequestMapping(value = "/v1/survey/template/{templateId}", method = RequestMethod.GET)
    public SurveyTemplate getTemplate(@PathVariable("templateId") Integer templateId);

    @RequestMapping(value = "/v1/survey/template/all", method = RequestMethod.GET)
    public Collection<SurveyTemplate> getAllTemplates();

}
