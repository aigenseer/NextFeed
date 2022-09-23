package com.nextfeed.service.repository.system;


import com.nextfeed.library.core.entity.system.SystemConfiguration;

import com.nextfeed.library.core.service.repository.SystemRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity.system")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/system-repository")
public class SystemRepositoryRestController implements SystemRepositoryService {

    public static void main(String[] args) {
        SpringApplication.run(SystemRepositoryRestController.class, args);
    }

    private final SystemConfigurationDBService systemConfigurationDBService;

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public SystemConfiguration save(@RequestBody SystemConfiguration systemConfiguration) {
        return systemConfigurationDBService.save(systemConfiguration);
    }

    @RequestMapping(value = "/v1/get/name", method = RequestMethod.POST)
    public Optional<SystemConfiguration> getByName(@RequestBody String name) {
        return systemConfigurationDBService.getByName(name);
    }

}
