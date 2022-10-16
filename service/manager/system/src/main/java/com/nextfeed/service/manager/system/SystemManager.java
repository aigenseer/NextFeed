package com.nextfeed.service.manager.system;

import com.nextfeed.library.core.entity.system.SystemConfiguration;
import com.nextfeed.library.core.grpc.service.repository.SystemRepositoryServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SystemManager{

    private final SystemRepositoryServiceClient systemRepositoryServiceClient;

    public DTOEntities.SystemConfigurationDTO create(String name, String value){
        DTOEntities.SystemConfigurationDTO configuration = DTOEntities.SystemConfigurationDTO.newBuilder().setName(name).setValue(value).build();
        systemRepositoryServiceClient.save(configuration);
        return configuration;
    }

    public Optional<DTOEntities.SystemConfigurationDTO> getByName(String name){
        return systemRepositoryServiceClient.getByName(name);
    }

    public Boolean existsByName(String name){
        return systemRepositoryServiceClient.getByName(name).isPresent();
    }



}
