package com.nextfeed.service.repository.system;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.UserRepositoryServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.nextfeed.library.core.entity.user")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RequiredArgsConstructor
@GrpcService
public class UserRepositoryGRPCService extends UserRepositoryServiceGrpc.UserRepositoryServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(UserRepositoryGRPCService.class, args);
    }

    private final UserDBService userDBService;

    @Override
    public void save(DTOEntities.UserDTO userDTO, StreamObserver<DTOEntities.UserDTO> responseObserver) {
        var u = DTO2EntityUtils.dto2User(userDTO);
        u = userDBService.save(u);
        responseObserver.onNext(Entity2DTOUtils.user2DTO(u));
        responseObserver.onCompleted();
    }

    @Override
    public void findById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalUserDTO> responseObserver) {
        var u = userDBService.findById(request.getId());
        responseObserver.onNext(DTOEntities.OptionalUserDTO.newBuilder().setUserDTO(Entity2DTOUtils.user2DTO(u)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUsersByMailAddress(Requests.SearchRequest request, StreamObserver<DTOEntities.OptionalUserDTO> responseObserver) {
        var u = userDBService.getUsersByMailAddress(request.getSearch());
        var builder = DTOEntities.OptionalUserDTO.newBuilder();
        if(u.isPresent()){
            builder.setUserDTO(Entity2DTOUtils.user2DTO(u.get()));
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

}
