package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.proto.repository.*;
import com.nextfeed.library.core.utils.MicroserviceUtils;
import com.nextfeed.library.core.valueobject.survey.SurveyValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SurveySocketServices{

    private final MicroserviceUtils serviceUtils;
    private final static String INSTANCE_NAME = "survey-service";

    @Value("#{new Integer('${nextfeed.service.survey-service.grpc-port}')}")
    private Integer port;

    public void onCreateByPresenter(Integer sessionId, SurveyValue surveyValue){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("static://%s:%s".formatted(instance.getIPAddr(), port)).usePlaintext().build();
                SurveySocketServiceGrpc.SurveySocketServiceBlockingStub blockingStub = SurveySocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.onCreateByPresenter(CreateByPresenterRequest.newBuilder().setSessionId(sessionId).setSurvey(surveyValue.getDTO()).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }


    public void onCreateByParticipant(Integer sessionId, Integer surveyId, SurveyTemplateValue template){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("static://%s:%s".formatted(instance.getIPAddr(), port)).usePlaintext().build();
                SurveySocketServiceGrpc.SurveySocketServiceBlockingStub blockingStub = SurveySocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.onCreateByParticipant(CreateByParticipantRequest.newBuilder().setSessionId(sessionId).setSurveyId(surveyId).setTemplate(template.getDTO()).build());
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
                ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("static://%s:%s".formatted(instance.getIPAddr(), port)).usePlaintext().build();
                SurveySocketServiceGrpc.SurveySocketServiceBlockingStub blockingStub = SurveySocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.onClose(CloseRequest.newBuilder().setSessionId(sessionId).setSurveyId(surveyId).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

    public void onUpdate(Integer sessionId, SurveyValue survey){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("static://%s:%s".formatted(instance.getIPAddr(), port)).usePlaintext().build();
                SurveySocketServiceGrpc.SurveySocketServiceBlockingStub blockingStub = SurveySocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.onUpdate(UpdateRequest.newBuilder().setSessionId(sessionId).setSurveyDTO(survey.getDTO()).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

    public void onResult(Integer sessionId, SurveyValue survey){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("static://%s:%s".formatted(instance.getIPAddr(), port)).usePlaintext().build();
                SurveySocketServiceGrpc.SurveySocketServiceBlockingStub blockingStub = SurveySocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.onResult(ResultRequest.newBuilder().setSessionId(sessionId).setSurveyDTO(survey.getDTO()).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

}
