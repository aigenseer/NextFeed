package com.nextfeed.service.manager.system;


import com.nextfeed.library.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class DefaultConfigsManager {

    private final SystemManager systemManager;

    @PostConstruct
    public void init(){
        initJWTSecret();
    }

    private void initJWTSecret(){
        final String configName = "jwt.secret";
        if(!systemManager.existsByName(configName))
            systemManager.create(configName, StringUtils.randomString(20));
    }

}
