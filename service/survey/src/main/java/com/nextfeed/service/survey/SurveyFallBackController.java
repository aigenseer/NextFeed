package com.nextfeed.service.survey;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/survey", produces = MediaType.APPLICATION_JSON_VALUE)
public class SurveyFallBackController implements SurveyService {

    @Override
    public TestRequest create() {
        System.out.println("Fallback called....");
        return new TestRequest("Test");
    }

}
