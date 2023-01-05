package com.nextfeed.service.core.session.adapter.primary.grpc;

import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.SharedCoreCacheService;
import com.nextfeed.library.core.proto.manager.SessionManagerServiceGrpc;
import com.nextfeed.library.core.proto.manager.ShareCacheResponse;
import com.nextfeed.library.core.proto.response.Response;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@AllArgsConstructor
@GrpcService
public class SessionManagerGRPCService extends SessionManagerServiceGrpc.SessionManagerServiceImplBase {

    private final SharedCoreCacheService sharedCoreCacheService;

    @Override
    public void getShareCache(Response.Empty e, StreamObserver<ShareCacheResponse> responseObserver) {
        responseObserver.onNext(sharedCoreCacheService.getSerializedCache());
        responseObserver.onCompleted();
    }








}
