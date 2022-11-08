package com.nextfeed.service.core.mood.adapter.primary;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.MoodRepositoryServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import com.nextfeed.service.core.mood.core.db.MoodDBService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@RequiredArgsConstructor
@GrpcService
public class MoodRepositoryGRPCService extends MoodRepositoryServiceGrpc.MoodRepositoryServiceImplBase {

    private final MoodDBService moodDBService;

    @Override
    public void save(DTOEntities.MoodEntityDTO dto, StreamObserver<DTOEntities.MoodEntityDTO> responseObserver) {
        var e = DTO2EntityUtils.dto2MoodEntity(dto);
        e = moodDBService.save(e);
        responseObserver.onNext(Entity2DTOUtils.moodEntity2DTO(e));
        responseObserver.onCompleted();
    }

    @Override
    public void findBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.MoodEntityDTOList> responseObserver) {
        var list = moodDBService.getRepo().findBySessionId(request.getId());
        responseObserver.onNext(DTOListUtils.moodEntities2DTO(list));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAllBySessionId(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        moodDBService.getRepo().deleteAllBySessionId(request.getId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

}
