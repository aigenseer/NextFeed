package com.nextfeed.library.core.utils;

import com.google.gson.reflect.TypeToken;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class KubernetesServiceUtils {

    private final static String TOKEN_FILE_PATH = "/var/run/secrets/kubernetes.io/serviceaccount/token";
    private static String token = null;

    @Value("${nextfeed.kubernetes.podLabelKey}")
    private String POD_LABEL_KEY;

    @Value("${nextfeed.kubernetes.namespace}")
    private String NAMESPACE;

    @Value("#{new Boolean('${nextfeed.debug}')}")
    private Boolean debug;

    @PostConstruct
    public void init(){
        if(!debug){
            initToken();
        }
    }

    private void initToken(){
        File f = new File(TOKEN_FILE_PATH);
        if(f.exists() && !f.isDirectory()) {
            token = readFile(TOKEN_FILE_PATH);
        }else{
            throw new RuntimeException("File not exists "+ TOKEN_FILE_PATH);
        }
    }

    private String readFile(String filePath){
        try
        {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            return new String (bytes);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getPodIpsByServiceName(String serviceName){
        var podList = listNamespacedPodsByLabelSelector(POD_LABEL_KEY, serviceName);
        return podList.getItems().stream().map(p -> Objects.requireNonNull(p.getStatus()).getPodIP()).toList();
    }

    private V1PodList listNamespacedPodsByLabelSelector(String label, String serviceName){
        final StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("https://kubernetes.default.svc/api/v1/namespaces/%s/pods".formatted(NAMESPACE));
        uriBuilder.append("?").append("labelSelector").append("=").append("%s=%s".formatted(label, serviceName));
        Type localVarReturnType = (new TypeToken<V1PodList>() {}).getType();
        return call(token, uriBuilder.toString(), localVarReturnType);

    }

    private HttpGet getAuthorizationHttpGet(String token, String url){
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return httpGet;
    }

    private CloseableHttpClient getHttpClient(){
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T call(String token, String url, Type localVarReturnType){
        try (CloseableHttpClient httpClient = getHttpClient()){
            if(httpClient==null) throw new RuntimeException("HttpClient are null");
            HttpGet httpGet = getAuthorizationHttpGet(token, url);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    return Config.defaultClient().getJSON().deserialize(result, localVarReturnType);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
