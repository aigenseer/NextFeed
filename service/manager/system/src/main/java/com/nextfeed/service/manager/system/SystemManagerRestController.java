package com.nextfeed.service.manager.system;


import com.nextfeed.library.core.entity.SystemConfiguration;
import com.nextfeed.library.core.service.manager.SystemManagerService;
import com.nextfeed.library.core.service.manager.dto.system.GetConfigurationRequest;
import com.nextfeed.library.core.service.manager.dto.system.NewConfigurationRequest;
import lombok.AllArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories("com.nextfeed.library.manager.repository")
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/system-manager")
public class SystemManagerRestController implements SystemManagerService {

    public static void main(String[] args) {
        SpringApplication.run(SystemManagerRestController.class, args);
    }

    private final SystemManager systemManager;

    @RequestMapping(value = "/v1/system/create", method = RequestMethod.POST)
    public SystemConfiguration create(@RequestBody NewConfigurationRequest request) {
        return systemManager.create(request.getName(), request.getValue());
    }

    @RequestMapping(value = "/v1/system/get", method = RequestMethod.POST)
    public SystemConfiguration get(@RequestBody GetConfigurationRequest request) {
        return systemManager.getByName(request.getName());
    }

}
