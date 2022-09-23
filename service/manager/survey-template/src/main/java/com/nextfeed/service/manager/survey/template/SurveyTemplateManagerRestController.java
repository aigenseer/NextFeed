package com.nextfeed.service.manager.survey.template;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.manager.SurveyTemplateManagerService;
import lombok.AllArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/survey-template-manager")
public class SurveyTemplateManagerRestController implements SurveyTemplateManagerService {

    public static void main(String[] args) {
        SpringApplication.run(SurveyTemplateManagerRestController.class, args);
    }

    private final SurveyTemplateManager surveyTemplateManager;

    @RequestMapping(value = "/v1/survey/template/create", method = RequestMethod.POST)
    public SurveyTemplate createTemplate(@RequestBody SurveyTemplate template) {
        return surveyTemplateManager.createTemplate(template);
    }

    @RequestMapping(value = "/v1/survey/template/{templateId}", method = RequestMethod.GET)
    public SurveyTemplate getTemplate(@PathVariable("templateId") Integer templateId) {
        return surveyTemplateManager.getTemplateById(templateId);
    }

    @RequestMapping(value = "/v1/survey/template/all", method = RequestMethod.GET)
    public Collection<SurveyTemplate> getAllTemplates() {
        return surveyTemplateManager.getAllTemplates();
    }

}
