package com.nextfeed.service.core.survey.adapter.primary.rest;

import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.external.dto.survey.MessageRequest;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.service.core.survey.ports.incoming.ISurveyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/survey-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class SurveyRestController {

    private final ISurveyManager surveyManager;
    private final ServiceUtils serviceUtils;
    //todo: muss noch gemacht
//    private final TokenService tokenService;


    @GetMapping("/presenter/v1/session/{sessionId}/survey/templates")
    public List<SurveyTemplate> getSessionTemplates(@PathVariable int sessionId){
        serviceUtils.checkSessionId(sessionId);
        return surveyManager.findAllTemplates().getEntities();
    }

    @GetMapping("/presenter/v1/session/{sessionId}/surveys")
    public List<Survey> getSessionSurveys(@PathVariable int sessionId){
        serviceUtils.checkSessionId(sessionId);
        return surveyManager.getSurveysBySessionId(sessionId).getEntities();
    }

    @GetMapping("/presenter/v1/session/{sessionId}/survey/create/{templateId}")
    public void create(@PathVariable int sessionId, @PathVariable int templateId){
        serviceUtils.checkSessionId(sessionId);
        serviceUtils.checkTemplateId(templateId);
        surveyManager.createSurvey(sessionId, surveyManager.findTemplateById(templateId).get());
    }

    @PostMapping("/presenter/v1/session/{sessionId}/survey/create")
    public SurveyTemplate create(@PathVariable int sessionId, @RequestBody SurveyTemplate template){
        serviceUtils.checkSessionId(sessionId);
        surveyManager.createSurvey(sessionId, surveyManager.saveTemplate(template));
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
        surveyManager.addAnswerToSurvey(sessionId, surveyId, 0, request.getText());

    }

}
