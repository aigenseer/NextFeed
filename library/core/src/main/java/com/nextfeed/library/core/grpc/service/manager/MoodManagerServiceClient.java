package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.manager.MoodManagerServiceGrpc;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.valueobject.mood.MoodValueList;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class MoodManagerServiceClient {

    @GrpcClient("mood-service")
    private MoodManagerServiceGrpc.MoodManagerServiceBlockingStub rpcService;

    public MoodValueList findBySessionId(Integer id) {
        return MoodValueList.createByDTO(rpcService.findBySessionId(DTORequestUtils.createIDRequest(id)));
    }



}
