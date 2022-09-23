package com.nextfeed.service.manager.system;

import com.nextfeed.library.core.entity.SystemConfiguration;
import com.nextfeed.library.core.service.repository.SystemRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemManager{

    private final SystemRepositoryService systemRepositoryService;

    public SystemConfiguration create(String name, String value){
        SystemConfiguration configuration = SystemConfiguration.builder().name(name).value(value).build();
        systemRepositoryService.save(configuration);
        return configuration;
    }

    public SystemConfiguration getByName(String name){
        return systemRepositoryService.getByName(name).orElse(null);
    }

    public Boolean existsByName(String name){
        return systemRepositoryService.getByName(name).isPresent();
    }



}
