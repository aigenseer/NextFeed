package com.nextfeed.service.external.survey;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.external.dto.survey.MessageRequest;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.library.core.service.manager.*;
import com.nextfeed.library.core.service.manager.dto.survey.SurveyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;



@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/survey-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class SurveyRestController {

    public static void main(String[] args) {
        SpringApplication.run(SurveyRestController.class, args);
    }

    private final SurveyManagerService surveyManagerService;
    private final SurveyTemplateManagerService surveyTemplateManagerService;
    private final ServiceUtils serviceUtils;
    //todo: muss noch gemacht
//    private final TokenService tokenService;


    @GetMapping("/presenter/v1/session/{sessionId}/survey/templates")
    public Collection<SurveyTemplate> getSessionTemplates(@PathVariable int sessionId){
        serviceUtils.checkSessionId(sessionId);
        return surveyTemplateManagerService.getAllTemplates();
    }

    @GetMapping("/presenter/v1/session/{sessionId}/surveys")
    public List<SurveyDTO> getSessionSurveys(@PathVariable int sessionId){
        serviceUtils.checkSessionId(sessionId);
        return surveyManagerService.getSurveys(sessionId);
    }

    @GetMapping("/presenter/v1/session/{sessionId}/survey/create/{templateId}")
    public void create(@PathVariable int sessionId, @PathVariable int templateId){
        serviceUtils.checkSessionId(sessionId);
        serviceUtils.checkTemplateId(templateId);
        surveyManagerService.createSurvey(sessionId, surveyTemplateManagerService.getTemplate(templateId));
    }

    @PostMapping("/presenter/v1/session/{sessionId}/survey/create")
    public SurveyTemplate create(@PathVariable int sessionId, @RequestBody SurveyTemplate template){
        serviceUtils.checkSessionId(sessionId);
        surveyManagerService.createSurvey(sessionId, surveyTemplateManagerService.createTemplate(template));
        return template;
    }

    @PostMapping("/participant/v1/session/{sessionId}/survey/{surveyId}/answer")
    public void setAnswer(@PathVariable int sessionId, @PathVariable int surveyId, @RequestBody MessageRequest request, @RequestHeader("Authorization") String token){
        //todo: muss noch gemacht werden
//        tokenService.checkSessionIdByToken(token, sessionId);
        serviceUtils.checkSessionId(sessionId);
        //todo: muss noch gemacht werden
//        SecurityContextHolderUtils.getCurrentAuthenticationId().ifPresent(id ->
//                surveyManagerService.addAnswerToSurvey(sessionId, surveyId, id, request.getText())
//        );
        surveyManagerService.addAnswerToSurvey(sessionId, surveyId, 0, request.getText());

    }

}
