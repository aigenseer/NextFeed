package com.nextfeed.service.repository.system;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.SystemRepositoryServiceGrpc;
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

@EntityScan("com.nextfeed.library.core.entity.system")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RequiredArgsConstructor
@GrpcService
public class SystemRepositoryGRPCService extends SystemRepositoryServiceGrpc.SystemRepositoryServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(SystemRepositoryGRPCService.class, args);
    }

    private final SystemConfigurationDBService systemConfigurationDBService;

    @Override
    public void save(DTOEntities.SystemConfigurationDTO systemConfigurationDTO, StreamObserver<DTOEntities.SystemConfigurationDTO> responseObserver) {
        var s = DTO2EntityUtils.dto2SystemConfiguration(systemConfigurationDTO);
        s = systemConfigurationDBService.save(s);
        responseObserver.onNext(Entity2DTOUtils.systemConfiguration2DTO(s));
        responseObserver.onCompleted();
    }

    @Override
    public void getByName(Requests.SearchRequest request, StreamObserver<DTOEntities.OptionalSystemConfigurationDTO> responseObserver) {
        var s = systemConfigurationDBService.getByName(request.getSearch());
        var dto = Entity2DTOUtils.systemConfiguration2DTO(s.get());
        responseObserver.onNext(DTOEntities.OptionalSystemConfigurationDTO.newBuilder().setSystemConfigurationDTO(dto).build());
        responseObserver.onCompleted();
    }



}
