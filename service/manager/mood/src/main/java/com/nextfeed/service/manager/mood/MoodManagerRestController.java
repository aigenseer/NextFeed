package com.nextfeed.service.manager.mood;


import com.nextfeed.library.core.entity.MoodEntity;
import com.nextfeed.library.core.service.manager.MoodManagerService;
import com.nextfeed.library.core.service.manager.dto.mood.NewCalculatedMoodRequest;
import com.nextfeed.library.core.service.manager.dto.mood.NewMoodRequest;
import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories("com.nextfeed.library.manager.repository")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RefreshScope
@RequiredArgsConstructor
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
