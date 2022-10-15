package com.nextfeed.library.core.grpc.service.repository;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.UserRepositoryServiceGrpc;
import com.nextfeed.library.core.utils.DTORequestUtils;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.Optional;


public class UserRepositoryServiceClient {

    @GrpcClient("user-repository-service")
    private UserRepositoryServiceGrpc.UserRepositoryServiceBlockingStub rpcService;

    public DTOEntities.UserDTO save(DTOEntities.UserDTO dto) {
        return rpcService.save(dto);
    }

    public Optional<DTOEntities.UserDTO> findById(Integer id) {
        return Optional.of(rpcService.findById(DTORequestUtils.createIDRequest(id)).getUserDTO());
    }

    public Optional<DTOEntities.UserDTO> getUsersByMailAddress(String mail) {
        return Optional.of(rpcService.getUsersByMailAddress(DTORequestUtils.createSearchRequest(mail)).getUserDTO());
    }

}
