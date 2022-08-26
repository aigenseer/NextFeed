package com.nextfeed.service.socket.survey;

import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.socket.QuestionSocketService;
import com.nextfeed.library.core.service.socket.SurveySocketService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@EnableHystrix
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/survey-socket")
public class SurveySocketRestController implements SurveySocketService {

    public static void main(String[] args) {
        SpringApplication.run(SurveySocketRestController.class, args);
    }

    private final SurveyService surveyService;

    @RequestMapping(value = "/v1/session/{sessionId}/survey/admin", method = RequestMethod.POST)
    public void onCreateByAdmin(@PathVariable("sessionId") Integer sessionId, @RequestBody Survey survey){
        surveyService.onCreateByAdmin(sessionId, survey);
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
    public void onUpdate(@PathVariable("sessionId") Integer sessionId, @RequestBody Survey survey){
        surveyService.onUpdate(sessionId, survey);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/survey/result", method = RequestMethod.POST)
    public void onResult(@PathVariable("sessionId") Integer sessionId, @RequestBody Survey survey){
        surveyService.onResult(sessionId, survey);
    }



}




