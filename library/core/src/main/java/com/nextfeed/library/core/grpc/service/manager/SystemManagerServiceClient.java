package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.library.core.proto.requests.Requests;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.Optional;


public class SystemManagerServiceClient {

    @GrpcClient("system-manager-service")
    private SystemManagerServiceGrpc.SystemManagerServiceBlockingStub rpcService;

    public DTOEntities.SystemConfigurationDTO create(DTOEntities.SystemConfigurationDTO dto) {
        return rpcService.create(dto);
    }

    public Optional<DTOEntities.SystemConfigurationDTO> get(String name) {
        return Optional.of(rpcService.get(Requests.SearchRequest.newBuilder().setSearch(name).build()).getSystemConfigurationDTO());
    }

}
