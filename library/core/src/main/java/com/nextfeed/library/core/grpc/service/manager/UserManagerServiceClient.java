package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.library.core.valueobject.user.OptionalUserValue;
import com.nextfeed.library.core.valueobject.user.UserValue;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UserManagerServiceClient {

    @GrpcClient("user-management-service")
    private UserManagerServiceGrpc.UserManagerServiceBlockingStub rpcService;

    public UserValue createUser(String name, String mailAddress, String pw) {
        return UserValue.DTOBuilder().dto(rpcService.createUser(NewUserRequest.newBuilder().setName(name).setMailAddress(mailAddress).setPw(pw).build())).build();
    }

    public OptionalUserValue getUser(Integer id) {
        var optional = rpcService.getUser(GetUserRequest.newBuilder().setId(id).build());
        return OptionalUserValue.DTOBuilder().dto(optional).build();
    }

    public OptionalUserValue getUserByMailAddress(String mailAddress) {
        var optional = rpcService.getUserByMailAddress(UserByMailAddressRequest.newBuilder().setMailAddress(mailAddress).build());
        return OptionalUserValue.DTOBuilder().dto(optional).build();
    }

    public boolean validatePasswordByMailAddress(String mailAddress, String pw) {
        return rpcService.validatePasswordByMailAddress(ValidateUserRequest.newBuilder().setMailAddress(mailAddress).setPw(pw).build()).getResult();
    }

}
