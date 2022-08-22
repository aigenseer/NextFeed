package com.nextfeed.library.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiceUtils {

    private final EurekaClient eurekaClient;

    public List<InstanceInfo> getInstanceInfoByName(String instanceName){
        return eurekaClient.getApplication(instanceName).getInstances();
    }

    public String objectToJson(Object o){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T postRequest(URI uri, Object o, Class<T> resultClass){
        RestTemplate restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonData = objectToJson(o);
        HttpEntity<String> request = new HttpEntity<String>(jsonData, headers);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(uri, request, resultClass);
        return responseEntity.getBody();
    }

    public <T> T getRequest(URI uri, Class<T> resultClass){
        ResponseEntity<T> responseEntity = new RestTemplate().getForEntity(uri, resultClass);
        return responseEntity.getBody();
    }

    public URI getURIByInstance(InstanceInfo instance, String path){
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .port(instance.getPort())
                .host(instance.getIPAddr())
                .path(path)
                .build().toUri();
    }
}
