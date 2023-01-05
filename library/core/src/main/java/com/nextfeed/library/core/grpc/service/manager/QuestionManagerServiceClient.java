package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.manager.QuestionManagerServiceGrpc;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.valueobject.question.QuestionValueList;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class QuestionManagerServiceClient {

    @GrpcClient("question-service")
    private QuestionManagerServiceGrpc.QuestionManagerServiceBlockingStub rpcService;

    public QuestionValueList findBySessionId(Integer id) {
        return QuestionValueList.createByDTO(rpcService.findBySessionId(DTORequestUtils.createIDRequest(id)));
    }

}
