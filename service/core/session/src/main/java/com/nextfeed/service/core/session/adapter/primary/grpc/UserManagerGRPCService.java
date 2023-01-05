package com.nextfeed.service.core.session.adapter.primary.grpc;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.core.session.ports.incoming.usermanagement.IUserManager;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class UserManagerGRPCService extends UserManagerServiceGrpc.UserManagerServiceImplBase {

    private final IUserManager userManager;

    @Override
    public void createUser(NewUserRequest request, StreamObserver<DTOEntities.UserDTO> responseObserver) {
        var userValue = userManager.createUser(request.getMailAddress(), request.getName(), request.getPw());
        responseObserver.onNext(userValue.getDTO());
        responseObserver.onCompleted();
    }

    @Override
    public void getUser(GetUserRequest request, StreamObserver<DTOEntities.OptionalUserDTO> responseObserver) {
        var optionalUserValue = userManager.getUserById(request.getId());
        responseObserver.onNext(optionalUserValue.getOptionalDTO());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserByMailAddress(UserByMailAddressRequest request, StreamObserver<DTOEntities.OptionalUserDTO> responseObserver) {
        var optionalUserValue = userManager.getUserByMailAddress(request.getMailAddress());
        responseObserver.onNext(optionalUserValue.getOptionalDTO());
        responseObserver.onCompleted();
    }

    @Override
    public void validatePasswordByMailAddress(ValidateUserRequest request, StreamObserver<Response.BooleanResponse> responseObserver) {
        var b = userManager.validatePasswordByMailAddress(request.getMailAddress(), request.getMailAddress());
        responseObserver.onNext(DTOResponseUtils.createBooleanResponse(b));
        responseObserver.onCompleted();
    }

}
