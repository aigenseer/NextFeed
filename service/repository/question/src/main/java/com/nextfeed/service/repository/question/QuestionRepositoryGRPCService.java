package com.nextfeed.service.repository.question;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.entity.question.VoterEntity;
import com.nextfeed.library.core.grpc.service.repository.ParticipantRepositoryServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.QuestionRepositoryServiceGrpc;
import com.nextfeed.library.core.proto.repository.VoteQuestionRequest;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EntityScan("com.nextfeed.library.core.entity.question")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RequiredArgsConstructor
@GrpcService
public class QuestionRepositoryGRPCService extends QuestionRepositoryServiceGrpc.QuestionRepositoryServiceImplBase {

    public static void main(String[] args) {
        SpringApplication.run(QuestionRepositoryGRPCService.class, args);
    }

    private final QuestionDBService questionDBService;
    private final VoterDBService voterDBService;
    private final ParticipantRepositoryServiceClient participantRepositoryServiceClient;

    private DTOEntities.QuestionDTO toDTO(QuestionEntity e){
        if(e == null) return null;
        var p = participantRepositoryServiceClient.findById(e.getParticipant_id());
        var voters = voterDBService.getRepo().findByQuestionId(e.getId());
        return Entity2DTOUtils.question2DTO(e, p.get(), voters);
    }

    private DTOEntities.OptionalQuestionDTO toOptionalDTO(QuestionEntity e){
        return DTOEntities.OptionalQuestionDTO.newBuilder().setQuestion(toDTO(e)).build();
    }

    @Override
    public void save(DTOEntities.QuestionDTO dto, StreamObserver<DTOEntities.QuestionDTO> responseObserver) {
        var e = DTO2EntityUtils.dto2Question(dto);
        e = questionDBService.save(e);
        responseObserver.onNext(toDTO(e));
        responseObserver.onCompleted();
    }

    @Override
    public void findById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalQuestionDTO> responseObserver) {
        var e = questionDBService.findById(request.getId());
        responseObserver.onNext(toOptionalDTO(e));
        responseObserver.onCompleted();
    }

    @Override
    public void addVote(VoteQuestionRequest request, StreamObserver<Response.Empty> responseObserver) {
        voterDBService.save(VoterEntity.builder()
                .question_id(request.getQuestionId())
                .participant_id(request.getParticipantId())
                .rating(request.getRating())
                .build());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    public DTOEntities.QuestionDTOList questionList2DTO(List<QuestionEntity> list){
        return DTOListUtils.toQuestionDTOList(list.stream().map(this::toDTO).toList());
    }

    @Override
    public void findBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.QuestionDTOList> responseObserver) {
        var list = questionDBService.getRepo().findBySessionId(request.getId());
        responseObserver.onNext(questionList2DTO(list));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAllBySessionId(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        questionDBService.getRepo().deleteAllBySessionId(request.getId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void close(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalQuestionDTO> responseObserver) {
        var o = questionDBService.getRepo().findById(request.getId());
        QuestionEntity e = null;
        if(o.isPresent()){
            e = o.get();
            e.setClosed(System.currentTimeMillis());
        }
        responseObserver.onNext(toOptionalDTO(e));
        responseObserver.onCompleted();
    }

}
