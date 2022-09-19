package com.nextfeed.service.socket.survey;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.manager.SurveyManagerService;
import com.nextfeed.library.core.service.manager.dto.survey.SurveyDTO;
import com.nextfeed.library.core.service.socket.SurveySocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/internal/survey-socket")
public class SurveySocketRestController implements SurveySocketService {

    public static void main(String[] args) {
        SpringApplication.run(SurveySocketRestController.class, args);
    }

    private final SurveyService surveyService;
    private final SurveyManagerService surveyManagerService;

    @RequestMapping(value = "/v1/session/{sessionId}/survey/presenter", method = RequestMethod.POST)
    public void onCreateByPresenter(@PathVariable("sessionId") Integer sessionId, @RequestBody SurveyDTO surveyDTO){
        surveyService.onCreateByPresenter(sessionId, surveyDTO);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/survey/{surveyId}", method = RequestMethod.POST)
    public void onCreateByParticipant(@PathVariable("sessionId") Integer sessionId, @PathVariable("surveyId") Integer surveyId, @RequestBody SurveyTemplate template){
        surveyService.onCreateByParticipant(sessionId, surveyId, template);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/survey/{surveyId}/close", method = RequestMethod.GET)
    public void onClose(@PathVariable("sessionId") Integer sessionId, @PathVariable("surveyId") Integer surveyId){
        surveyService.onClose(sessionId, surveyId);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/survey/update", method = RequestMethod.POST)
    public void onUpdate(@PathVariable("sessionId") Integer sessionId, @RequestBody SurveyDTO surveyDTO){
        surveyService.onUpdate(sessionId, surveyDTO);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/survey/result", method = RequestMethod.POST)
    public void onResult(@PathVariable("sessionId") Integer sessionId, @RequestBody SurveyDTO surveyDTO){
        if(surveyDTO!=null){
            surveyService.onResult(sessionId, surveyDTO);
        }
    }



}




