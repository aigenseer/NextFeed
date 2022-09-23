package com.nextfeed.service.repository.system;


import com.nextfeed.library.core.entity.system.SystemConfiguration;
import com.nextfeed.library.manager.repository.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemConfigurationDBService extends AbstractService<SystemConfiguration, SystemConfigurationRepository> {
    public SystemConfigurationDBService(SystemConfigurationRepository systemConfigurationRepository) {
        super(systemConfigurationRepository);
    }

    public Optional<SystemConfiguration> getByName(String name){
        return repo.findAllByName(name).stream().findFirst();
    }
}
