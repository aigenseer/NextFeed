package com.nextfeed.service.manager.system;

import com.nextfeed.library.core.entity.SystemConfiguration;
import com.nextfeed.library.manager.repository.service.SystemConfigurationDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemManager{

    private final SystemConfigurationDBService systemConfigurationDBService;

    public SystemConfiguration create(String name, String value){
        SystemConfiguration configuration = SystemConfiguration.builder().name(name).value(value).build();
        systemConfigurationDBService.save(configuration);
        return configuration;
    }

    public SystemConfiguration getByName(String name){
        return systemConfigurationDBService.getByName(name).orElse(null);
    }

    public Boolean existsByName(String name){
        return systemConfigurationDBService.getByName(name).isPresent();
    }



}
