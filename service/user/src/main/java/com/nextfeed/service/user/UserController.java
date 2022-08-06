package com.nextfeed.service.user;


import com.nextfeed.service.survey.SurveyService;
import com.nextfeed.service.survey.TestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final SurveyService surveyService;

    @GetMapping("/create")
    public UserRequest create() {
        TestRequest testRequest = surveyService.create();
        System.out.printf("yes %s", testRequest.test());
        return new UserRequest("TestUser");
    }

}
