package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.*;
import com.nextfeed.library.core.utils.SocketServiceUtils;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SurveySocketServices{

    private final SocketServiceUtils serviceUtils;
    private final static String INSTANCE_NAME = "survey-socket-service";

    @Value("#{new Integer('${nextfeed.service.survey-socket-service.port}')}")
    private Integer port;

    public void onCreateByPresenter(Integer sessionId, DTOEntities.SurveyDTO surveyDTO){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(instance.getIPAddr(), port).usePlaintext().build();
                SurveySocketServiceGrpc.SurveySocketServiceBlockingStub blockingStub = SurveySocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.onCreateByPresenter(CreateByPresenterRequest.newBuilder().setSessionId(sessionId).setSurvey(surveyDTO).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }


    public void onCreateByParticipant(Integer sessionId, Integer surveyId, DTOEntities.SurveyTemplateDTO template){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(instance.getIPAddr(), port).usePlaintext().build();
                SurveySocketServiceGrpc.SurveySocketServiceBlockingStub blockingStub = SurveySocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.onCreateByParticipant(CreateByParticipantRequest.newBuilder().setSessionId(sessionId).setSurveyId(surveyId).setTemplate(template).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

    public void onClose(Integer sessionId, Integer surveyId){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(instance.getIPAddr(), port).usePlaintext().build();
                SurveySocketServiceGrpc.SurveySocketServiceBlockingStub blockingStub = SurveySocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.onClose(CloseRequest.newBuilder().setSessionId(sessionId).setSurveyId(surveyId).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

    public void onUpdate(Integer sessionId, DTOEntities.SurveyDTO surveyDTO){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(instance.getIPAddr(), port).usePlaintext().build();
                SurveySocketServiceGrpc.SurveySocketServiceBlockingStub blockingStub = SurveySocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.onUpdate(UpdateRequest.newBuilder().setSessionId(sessionId).setSurveyDTO(surveyDTO).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

    public void onResult(Integer sessionId, DTOEntities.SurveyDTO surveyDTO){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(instance.getIPAddr(), port).usePlaintext().build();
                SurveySocketServiceGrpc.SurveySocketServiceBlockingStub blockingStub = SurveySocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.onResult(ResultRequest.newBuilder().setSessionId(sessionId).setSurveyDTO(surveyDTO).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

}
