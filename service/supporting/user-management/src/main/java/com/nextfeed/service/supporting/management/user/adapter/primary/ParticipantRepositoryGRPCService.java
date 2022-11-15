package com.nextfeed.service.supporting.management.user.adapter.primary;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.ParticipantRepositoryServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import com.nextfeed.service.supporting.management.user.ports.incoming.IParticipantRepositoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class ParticipantRepositoryGRPCService extends ParticipantRepositoryServiceGrpc.ParticipantRepositoryServiceImplBase {

    private final IParticipantRepositoryService participantRepositoryService;

    @Override
    public void save(DTOEntities.ParticipantDTO participantDTO, StreamObserver<DTOEntities.ParticipantDTO> responseObserver) {
        var p = DTO2EntityUtils.dto2Participant(participantDTO);
        p = participantRepositoryService.save(p);
        responseObserver.onNext(Entity2DTOUtils.participant2DTO(p));
        responseObserver.onCompleted();
    }

    @Override
    public void findById(Requests.IDRequest request, StreamObserver<DTOEntities.OptionalParticipantDTO> responseObserver) {
        var p = participantRepositoryService.findById(request.getId());
        responseObserver.onNext(DTOEntities.OptionalParticipantDTO.newBuilder().setParticipant(Entity2DTOUtils.participant2DTO(p)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void findBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.ParticipantDTOList> responseObserver) {
        var pList = participantRepositoryService.findBySessionId(request.getId());
        var list = DTOListUtils.participantList2DTO(pList);
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAllBySessionId(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        participantRepositoryService.deleteAllBySessionId(request.getId());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

}
