package com.nextfeed.service.supporting.management.user.core.participant.db;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.ParticipantRepositoryServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.nextfeed.library.core.entity.participant")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RequiredArgsConstructor
@GrpcService
public class ParticipantRepositoryGRPCService extends ParticipantRepositoryServiceGrpc.ParticipantRepositoryServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(ParticipantRepositoryGRPCService.class, args);
    }
    private final ParticipantDBService participantDBService;

    @Override
    public void save(DTOEntities.ParticipantDTO participantDTO, StreamObserver<DTOEntities.ParticipantDTO> responseObserver) {
        var p = DTO2EntityUtils.dto2Participant(participantDTO);
        p = participantDBService.save(p);
        responseObserver.onNext(Entity2DTOUtils.participant2DTO(p));
        responseObserver.onCompleted();
    }

    @Override
    public void findById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalParticipantDTO> responseObserver) {
        var p = participantDBService.findById(request.getId());
        responseObserver.onNext(DTOEntities.OptionalParticipantDTO.newBuilder().setParticipant(Entity2DTOUtils.participant2DTO(p)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void findBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.ParticipantDTOList> responseObserver) {
        var pList = participantDBService.getRepo().findBySessionId(request.getId());
        var list = DTOListUtils.participantList2DTO(pList);
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAllBySessionId(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        participantDBService.getRepo().deleteAllBySessionId(request.getId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

}
