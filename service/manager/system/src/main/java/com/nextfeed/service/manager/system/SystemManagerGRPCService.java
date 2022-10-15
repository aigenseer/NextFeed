package com.nextfeed.service.manager.system;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.SystemManagerServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@AllArgsConstructor
@GrpcService
public class SystemManagerGRPCService extends SystemManagerServiceGrpc.SystemManagerServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(SystemManagerGRPCService.class, args);
    }
    private final SystemManager systemManager;

    @Override
    public void create(DTOEntities.SystemConfigurationDTO dto, StreamObserver<DTOEntities.SystemConfigurationDTO> responseObserver) {
        var e = systemManager.create(dto.getName(), dto.getValue());
        responseObserver.onNext(Entity2DTOUtils.systemConfiguration2DTO(e));
        responseObserver.onCompleted();
    }

    @Override
    public void get(Requests.SearchRequest request, StreamObserver<DTOEntities.OptionalSystemConfigurationDTO> responseObserver) {
        var e = systemManager.getByName(request.getSearch());
        var dto = Entity2DTOUtils.systemConfiguration2DTO(e);
        responseObserver.onNext(DTOEntities.OptionalSystemConfigurationDTO.newBuilder().setSystemConfigurationDTO(dto).build());
        responseObserver.onCompleted();
    }

}
