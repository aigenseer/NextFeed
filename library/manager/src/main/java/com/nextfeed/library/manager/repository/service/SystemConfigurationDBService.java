package com.nextfeed.library.manager.repository.service;


import com.nextfeed.library.core.entity.SystemConfiguration;
import com.nextfeed.library.core.entity.User;
import com.nextfeed.library.manager.repository.SystemConfigurationRepository;
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
