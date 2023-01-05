package com.nextfeed.service.core.survey.adapter.primary.grpc;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.SurveyManagerServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.service.core.survey.ports.incoming.ISurveyManager;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class SurveyManagerGRPCService extends SurveyManagerServiceGrpc.SurveyManagerServiceImplBase {

    private final ISurveyManager surveyManager;

    @Override
    public void getSurveysBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.SurveyDTOList> responseObserver) {
        var surveyValueList = surveyManager.getSurveysBySessionId(request.getId());
        responseObserver.onNext(surveyValueList.getDTOs());
        responseObserver.onCompleted();
    }

}
