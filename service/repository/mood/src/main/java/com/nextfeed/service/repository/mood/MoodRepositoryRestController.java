package com.nextfeed.service.repository.mood;

import com.nextfeed.library.core.entity.MoodEntity;
import com.nextfeed.library.core.service.repository.MoodRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user-repository")
public class MoodRepositoryRestController implements MoodRepositoryService {

    public static void main(String[] args) {
        SpringApplication.run(MoodRepositoryRestController.class, args);
    }

    private final MoodDBService moodDBService;

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public MoodEntity save(@RequestBody MoodEntity moodEntity) {
        return moodDBService.save(moodEntity);
    }

    @RequestMapping(value = "/v1/get/all/{sessionId}", method = RequestMethod.GET)
    public List<MoodEntity> get(@PathVariable("sessionId") Integer sessionId) {
        return List.of();
    }

}
