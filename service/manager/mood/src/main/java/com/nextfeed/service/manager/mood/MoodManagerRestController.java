package com.nextfeed.service.manager.mood;


import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.service.manager.MoodManagerService;
import com.nextfeed.library.core.service.manager.dto.mood.NewCalculatedMoodRequest;
import com.nextfeed.library.core.service.manager.dto.mood.NewMoodRequest;
import lombok.AllArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/mood-manager")
public class MoodManagerRestController implements MoodManagerService {

    public static void main(String[] args) {
        SpringApplication.run(MoodManagerRestController.class, args);
    }

    private final MoodManager moodManager;

    @RequestMapping(value = "/v1/session/{sessionId}/mood/create", method = RequestMethod.POST)
    public MoodEntity addMoodValueToSession(@PathVariable("sessionId") Integer sessionId, @RequestBody NewMoodRequest request) {
        return moodManager.addMoodValueToSession(sessionId, request);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/mood/calculated/create", method = RequestMethod.POST)
    public MoodEntity createCalculatedMoodValue(@PathVariable("sessionId") Integer sessionId, @RequestBody NewCalculatedMoodRequest request) {
        return moodManager.createCalculatedMoodValue(sessionId, request);
    }



}
