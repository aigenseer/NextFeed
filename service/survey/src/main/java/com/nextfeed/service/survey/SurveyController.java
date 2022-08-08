package com.nextfeed.service.survey;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RefreshScope
@RestController
@RequestMapping(value = "/survey", produces = MediaType.APPLICATION_JSON_VALUE)
public class SurveyController implements SurveyService {

    private final String uniqueID = UUID.randomUUID().toString();

    @Value("${preix.attribute}")
    private String password;

    @GetMapping("/test")
    public TestRequest create() {
        System.out.println("yes geht " +uniqueID+ "password: "+password);
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return new TestRequest("Test");
    }

}
