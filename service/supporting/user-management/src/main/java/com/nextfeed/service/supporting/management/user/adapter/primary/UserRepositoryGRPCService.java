package com.nextfeed.service.supporting.management.user.adapter.primary;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.UserRepositoryServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import com.nextfeed.service.supporting.management.user.core.user.db.UserDBService;
import com.nextfeed.service.supporting.management.user.ports.incoming.IUserRepositoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class UserRepositoryGRPCService extends UserRepositoryServiceGrpc.UserRepositoryServiceImplBase {

    private final IUserRepositoryService userRepositoryService;

    @Override
    public void save(DTOEntities.UserDTO userDTO, StreamObserver<DTOEntities.UserDTO> responseObserver) {
        var u = DTO2EntityUtils.dto2User(userDTO);
        u = userRepositoryService.save(u);
        responseObserver.onNext(Entity2DTOUtils.user2DTO(u));
        responseObserver.onCompleted();
    }

    @Override
    public void findById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalUserDTO> responseObserver) {
        var u = userRepositoryService.findById(request.getId());
        responseObserver.onNext(DTOEntities.OptionalUserDTO.newBuilder().setUserDTO(Entity2DTOUtils.user2DTO(u)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUsersByMailAddress(Requests.SearchRequest request, StreamObserver<DTOEntities.OptionalUserDTO> responseObserver) {
        var u = userRepositoryService.getUsersByMailAddress(request.getSearch());
        var builder = DTOEntities.OptionalUserDTO.newBuilder();
        if(u.isPresent()){
            builder.setUserDTO(Entity2DTOUtils.user2DTO(u.get()));
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

}
