package com.nextfeed.library.core.grpc.service.repository;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.SystemRepositoryServiceGrpc;
import com.nextfeed.library.core.utils.DTORequestUtils;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.Optional;


public class SystemRepositoryServiceClient {

    @GrpcClient("system-repository-service")
    private SystemRepositoryServiceGrpc.SystemRepositoryServiceBlockingStub rpcService;


    public DTOEntities.SystemConfigurationDTO save(DTOEntities.SystemConfigurationDTO dto) {
        return rpcService.save(dto);
    }

    public Optional<DTOEntities.SystemConfigurationDTO> getByName(String name) {
        return Optional.of(rpcService.getByName(DTORequestUtils.createSearchRequest(name)).getSystemConfigurationDTO());
    }

}
