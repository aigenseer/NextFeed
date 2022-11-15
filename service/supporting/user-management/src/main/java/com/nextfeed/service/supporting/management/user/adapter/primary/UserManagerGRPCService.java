package com.nextfeed.service.supporting.management.user.adapter.primary;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.supporting.management.user.ports.incoming.IUserManager;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class UserManagerGRPCService extends UserManagerServiceGrpc.UserManagerServiceImplBase {

    private final IUserManager userManager;

    @Override
    public void createUser(NewUserRequest request, StreamObserver<DTOEntities.UserDTO> responseObserver) {
        var dto = userManager.createUser(request.getMailAddress(), request.getName(), request.getPw());
        responseObserver.onNext(dto);
        responseObserver.onCompleted();
    }

    @Override
    public void getUser(GetUserRequest request, StreamObserver<DTOEntities.OptionalUserDTO> responseObserver) {
        var dto = userManager.getUserById(request.getId());
        responseObserver.onNext(DTOEntities.OptionalUserDTO.newBuilder().setUserDTO(dto.orElseGet(null)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserByMailAddress(UserByMailAddressRequest request, StreamObserver<DTOEntities.OptionalUserDTO> responseObserver) {
        var dto = userManager.getUserByMailAddress(request.getMailAddress());
        responseObserver.onNext(DTOEntities.OptionalUserDTO.newBuilder().setUserDTO(dto.orElseGet(null)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void validatePasswordByMailAddress(ValidateUserRequest request, StreamObserver<Response.BooleanResponse> responseObserver) {
        var b = userManager.validatePasswordByMailAddress(request.getMailAddress(), request.getMailAddress());
        responseObserver.onNext(DTOResponseUtils.createBooleanResponse(b));
        responseObserver.onCompleted();
    }

}
