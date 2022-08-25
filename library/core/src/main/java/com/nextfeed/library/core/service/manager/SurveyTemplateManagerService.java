package com.nextfeed.library.core.service.manager;

import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@FeignClient(name = "survey-template-manager-service")
@LoadBalancerClient(name = "survey-template-manager-service", configuration = LoadBalancerConfiguration.class)
public interface SurveyTemplateManagerService {

    @RequestMapping(value = "/api/survey-template-manager/v1/survey/template/create", method = RequestMethod.POST)
    public SurveyTemplate createTemplate(@RequestBody SurveyTemplate template);

    @RequestMapping(value = "/api/survey-template-manager/v1/survey/template/{templateId}", method = RequestMethod.GET)
    public SurveyTemplate getTemplate(@PathVariable("templateId") Integer templateId);

    @RequestMapping(value = "/api/survey-template-manager/v1/survey/template/all", method = RequestMethod.GET)
    public Collection<SurveyTemplate> getAllTemplates();

}
