package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.manager.dto.survey.SurveyDTO;
import com.nextfeed.library.core.utils.SocketServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SurveySocketServices implements SurveySocketService{

    private final SocketServiceUtils serviceUtils;
    private final static String INSTANCE_NAME = "survey-socket-service";

    @Value("#{new Integer('${nextfeed.service.survey-socket-service.port}')}")
    private Integer port;

    public void onCreateByPresenter(Integer sessionId, SurveyDTO surveyDTO){
        String path = "/api/internal/survey-socket/v1/session/%d/survey/presenter".formatted(sessionId);
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                serviceUtils.postRequest(serviceUtils.getURIByInstance(instance, port, path), surveyDTO, String.class);
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }


    public void onCreateByParticipant(Integer sessionId, Integer surveyId, SurveyTemplate template){
        String path = "/api/internal/survey-socket/v1/session/%d/survey/%d".formatted(sessionId, surveyId);
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                serviceUtils.postRequest(serviceUtils.getURIByInstance(instance, port, path), template, String.class);
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

    public void onClose(Integer sessionId, Integer surveyId){
        String path = "/api/internal/survey-socket/v1/session/%d/survey/%d/close".formatted(sessionId, surveyId);
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                serviceUtils.getRequest(serviceUtils.getURIByInstance(instance, port, path), String.class);
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

    public void onUpdate(Integer sessionId, SurveyDTO surveyDTO){
        String path = "/api/internal/survey-socket/v1/session/%d/survey/update".formatted(sessionId);
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                serviceUtils.postRequest(serviceUtils.getURIByInstance(instance, port, path), surveyDTO, String.class);
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

    public void onResult(Integer sessionId, SurveyDTO surveyDTO){
        String path = "/api/internal/survey-socket/v1/session/%d/survey/result".formatted(sessionId);
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                serviceUtils.postRequest(serviceUtils.getURIByInstance(instance, port, path), surveyDTO, String.class);
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

}
