package com.nextfeed.service.repository.session;

import com.nextfeed.library.core.grpc.service.repository.MoodRepositoryServiceClient;
import com.nextfeed.library.core.grpc.service.repository.ParticipantRepositoryServiceClient;
import com.nextfeed.library.core.grpc.service.repository.QuestionRepositoryServiceClient;
import com.nextfeed.library.core.grpc.service.repository.SurveyRepositoryServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.*;
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

import java.util.Date;

@EntityScan("com.nextfeed.library.core.entity.question")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RequiredArgsConstructor
@GrpcService
public class SessionRepositoryGRPCService extends SessionRepositoryServiceGrpc.SessionRepositoryServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(SessionRepositoryGRPCService.class, args);
    }

    private final SessionDBService sessionDBService;
    private final ParticipantRepositoryServiceClient participantRepositoryServiceClient;
    private final QuestionRepositoryServiceClient questionRepositoryServiceClient;
    private final SurveyRepositoryServiceClient surveyRepositoryServiceClient;
    private final MoodRepositoryServiceClient moodRepositoryServiceClient;

    private DTOEntities.SessionDTO mapEntityToDTO(DTOEntities.SessionEntityDTO dto){
        return DTOEntities.SessionDTO.newBuilder()
                .setId(dto.getId())
                .setParticipants(participantRepositoryServiceClient.findBySessionId(dto.getId()))
                .setQuestions(questionRepositoryServiceClient.findBySessionId(dto.getId()))
                .setSurveys(surveyRepositoryServiceClient.findBySessionId(dto.getId()))
                .setMoodEntities(moodRepositoryServiceClient.findBySessionId(dto.getId()))
                .setClosed(dto.getClosed())
                .setSessionCode(dto.getSessionCode())
                .setName(dto.getName())
                .build();
    }

    @Override
    public void save(DTOEntities.SessionEntityDTO dto, StreamObserver<DTOEntities.SessionDTO> responseObserver) {
        var e = DTO2EntityUtils.dto2SessionEntity(dto);
        e = sessionDBService.save(e);
        dto = Entity2DTOUtils.sessionEntity2DTO(e);
        responseObserver.onNext(mapEntityToDTO(dto));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteById(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        sessionDBService.deleteById(request.getId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(Response.Empty e, StreamObserver<DTOEntities.SessionDTOList> responseObserver) {
        var pList = sessionDBService.findAll().stream().map(Entity2DTOUtils::sessionEntity2DTO).map(this::mapEntityToDTO).toList();
        responseObserver.onNext(DTOListUtils.toSessionDTOList(pList));
        responseObserver.onCompleted();
    }

    @Override
    public void findAllOpen(Response.Empty e, StreamObserver<DTOEntities.SessionDTOList> responseObserver) {
        var pList = sessionDBService.findAllOpen().stream().map(Entity2DTOUtils::sessionEntity2DTO).map(this::mapEntityToDTO).toList();
        responseObserver.onNext(DTOListUtils.toSessionDTOList(pList));
        responseObserver.onCompleted();
    }

    @Override
    public void findAllClosed(Response.Empty e, StreamObserver<DTOEntities.SessionDTOList> responseObserver) {
        var pList = sessionDBService.findAllClosed().stream().map(Entity2DTOUtils::sessionEntity2DTO).map(this::mapEntityToDTO).toList();
        responseObserver.onNext(DTOListUtils.toSessionDTOList(pList));
        responseObserver.onCompleted();
    }

    @Override
    public void findById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSessionDTO> responseObserver) {
        var e = sessionDBService.findById(request.getId());
        var session = mapEntityToDTO(Entity2DTOUtils.sessionEntity2DTO(e));
        responseObserver.onNext(DTOEntities.OptionalSessionDTO.newBuilder().setSession(session).build());
        responseObserver.onCompleted();
    }

    @Override
    public void findEntityById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSessionEntityDTO> responseObserver) {
        var e = sessionDBService.findById(request.getId());
        responseObserver.onNext(DTOEntities.OptionalSessionEntityDTO.newBuilder().setSessionEntity(Entity2DTOUtils.sessionEntity2DTO(e)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void close(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalSessionEntityDTO> responseObserver) {
        DTOEntities.SessionEntityDTO dto = null;
        var e = sessionDBService.findById(request.getId());
        if (e != null){
            e.setClosed(new Date().getTime());
            e = sessionDBService.save(e);
            dto = Entity2DTOUtils.sessionEntity2DTO(e);
        }
        responseObserver.onNext(DTOEntities.OptionalSessionEntityDTO.newBuilder().setSessionEntity(dto).build());
        responseObserver.onCompleted();
    }

}
