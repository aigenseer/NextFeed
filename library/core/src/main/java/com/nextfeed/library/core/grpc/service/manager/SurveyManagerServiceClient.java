package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.manager.SurveyManagerServiceGrpc;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.valueobject.survey.SurveyValueList;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class SurveyManagerServiceClient {

    @GrpcClient("survey-service")
    private SurveyManagerServiceGrpc.SurveyManagerServiceBlockingStub rpcService;

    public SurveyValueList getSurveysBySessionId(Integer sessionId) {
        return SurveyValueList.createByDTO(rpcService.getSurveysBySessionId(DTORequestUtils.createIDRequest(sessionId)));
    }

}
