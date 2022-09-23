package com.nextfeed.service.manager.system;


import com.nextfeed.library.core.entity.system.SystemConfiguration;
import com.nextfeed.library.core.service.manager.SystemManagerService;
import com.nextfeed.library.core.service.manager.dto.system.GetConfigurationRequest;
import com.nextfeed.library.core.service.manager.dto.system.NewConfigurationRequest;
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
