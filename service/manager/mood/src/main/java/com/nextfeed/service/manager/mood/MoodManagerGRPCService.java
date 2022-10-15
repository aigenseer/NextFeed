package com.nextfeed.service.manager.mood;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@AllArgsConstructor
@GrpcService
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
public class MoodManagerGRPCService extends MoodManagerServiceGrpc.MoodManagerServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(MoodManagerGRPCService.class, args);
    }

    private final MoodManager moodManager;

    @Override
    public void addMoodValueToSession(AddMoodValueToSessionRequest request, StreamObserver<DTOEntities.MoodEntityDTO> responseObserver) {
        var dto = moodManager.addMoodValueToSession(request.getSessionId(), request.getNewMoodRequest());
        responseObserver.onNext(dto);
        responseObserver.onCompleted();
    }

    @Override
    public void createCalculatedMoodValue(CreateCalculatedMoodValueRequest request, StreamObserver<DTOEntities.MoodEntityDTO> responseObserver) {
        var dto = moodManager.createCalculatedMoodValue(request.getSessionId(), request.getNewCalculatedMoodRequest());
        responseObserver.onNext(dto);
        responseObserver.onCompleted();
    }

}
