package com.nextfeed.service.core.survey.adapter.primary.rest;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.grpc.service.manager.SurveyManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.SurveyTemplateManagerServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.service.external.dto.survey.MessageRequest;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/survey-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class SurveyRestController {

    private final SurveyManagerServiceClient surveyManagerServiceClient;
    private final SurveyTemplateManagerServiceClient surveyTemplateManagerServiceClient;
    private final ServiceUtils serviceUtils;
    //todo: muss noch gemacht
//    private final TokenService tokenService;


    @GetMapping("/presenter/v1/session/{sessionId}/survey/templates")
    public List<DTOEntities.SurveyTemplateDTO> getSessionTemplates(@PathVariable int sessionId){
        serviceUtils.checkSessionId(sessionId);
        return surveyTemplateManagerServiceClient.getAllTemplates().getSurveyTemplatesList();
    }

    @GetMapping("/presenter/v1/session/{sessionId}/surveys")
    public List<DTOEntities.SurveyDTO> getSessionSurveys(@PathVariable int sessionId){
        serviceUtils.checkSessionId(sessionId);
        return surveyManagerServiceClient.getSurveysBySessionId(sessionId).getSurveysList();
    }

    @GetMapping("/presenter/v1/session/{sessionId}/survey/create/{templateId}")
    public void create(@PathVariable int sessionId, @PathVariable int templateId){
        serviceUtils.checkSessionId(sessionId);
        serviceUtils.checkTemplateId(templateId);
        surveyManagerServiceClient.createSurvey(sessionId, surveyTemplateManagerServiceClient.getTemplateById(templateId).get());
    }

    @PostMapping("/presenter/v1/session/{sessionId}/survey/create")
    public SurveyTemplate create(@PathVariable int sessionId, @RequestBody SurveyTemplate template){
        serviceUtils.checkSessionId(sessionId);
        var dto = Entity2DTOUtils.surveyTemplate2DTO(template);
        surveyManagerServiceClient.createSurvey(sessionId, surveyTemplateManagerServiceClient.createTemplate(dto));
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
        surveyManagerServiceClient.addAnswerToSurvey(sessionId, surveyId, 0, request.getText());

    }

}
