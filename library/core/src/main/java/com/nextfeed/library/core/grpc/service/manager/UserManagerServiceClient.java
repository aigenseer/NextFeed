package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.Optional;


public class UserManagerServiceClient {

    @GrpcClient("user-manager-service")
    private UserManagerServiceGrpc.UserManagerServiceBlockingStub rpcService;

    public DTOEntities.UserDTO createUser(String name, String mailAddress, String pw) {
        return rpcService.createUser(NewUserRequest.newBuilder().setName(name).setMailAddress(mailAddress).setPw(pw).build());
    }

    public Optional<DTOEntities.UserDTO> getUser(Integer id) {
        return Optional.of(rpcService.getUser(GetUserRequest.newBuilder().setId(id).build()).getUserDTO());
    }

    public Optional<DTOEntities.UserDTO> getUserByMailAddress(String mailAddress) {
        return Optional.of(rpcService.getUserByMailAddress(UserByMailAddressRequest.newBuilder().setMailAddress(mailAddress).build()).getUserDTO());
    }

    public boolean validatePasswordByMailAddress(String mailAddress, String pw) {
        return rpcService.validatePasswordByMailAddress(ValidateUserRequest.newBuilder().setMailAddress(mailAddress).setPw(pw).build()).getResult();
    }

}
