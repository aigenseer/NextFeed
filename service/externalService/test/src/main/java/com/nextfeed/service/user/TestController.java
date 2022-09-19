package com.nextfeed.service.user;

import com.google.gson.reflect.TypeToken;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootApplication(scanBasePackages = "com.nextfeed")
public class TestController {

    private final static String TOKEN_FILE_PATH = "/var/run/secrets/kubernetes.io/serviceaccount/token";
    private final static String NAMESPACE = "nextfeed";
    private final static String POD_LABEL_KEY = "io.kompose.service";
    private static String token = null;

    public static void main(String[] args) {
        SpringApplication.run(TestController.class, args);
    }

    @PostConstruct
    public void init(){
        System.out.println("??");
        initToken();
        var list = getPodIpsByServiceName("system-manager-service");
        System.out.println(list);
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

    private List<String> getPodIpsByServiceName(String serviceName){
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

//    private void call(String serviceName, String token){
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        try {
//
//            SSLContextBuilder builder = new SSLContextBuilder();
//            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
//            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                    builder.build());
//            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
//                    sslsf).build();
//
//            final StringBuilder uriBuilder = new StringBuilder();
//            uriBuilder.append("https://kubernetes.default.svc/api/v1/namespaces/%s/pods".formatted(NAMESPACE));
//            uriBuilder.append("?").append("labelSelector").append("=").append("%s=%s".formatted(POD_LABEL_KEY, serviceName));
////            URIBuilder uriBuilder = new URIBuilder();
////            uriBuilder.setParameter("fieldSelector", escapeString("%s=%s".formatted(POD_LABEL_KEY, serviceName)));
//
//            String urlString = uriBuilder.toString();
//
//
//            System.out.println(urlString);
//            HttpGet httpGet = new HttpGet(urlString);
//
//
//            httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
//            CloseableHttpResponse response = httpclient.execute(httpGet);
//
//            System.out.println("executing request " + httpGet.getRequestLine());
//
//            try {
//
//                // Get HttpResponse Status
//                System.out.println(response.getProtocolVersion());              // HTTP/1.1
//                System.out.println(response.getStatusLine().getStatusCode());   // 200
//                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
//                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
//
//                HttpEntity entity = response.getEntity();
//                if (entity != null) {
//                    // return it as a String
//                    String result = EntityUtils.toString(entity);
//                    System.out.println(result);
//                    Type localVarReturnType = (new TypeToken<V1PodList>() {}).getType();
//                    V1PodList podList = Config.defaultClient().getJSON().deserialize(result, localVarReturnType);
//                    var list = podList.getItems().stream().map(p -> Objects.requireNonNull(p.getStatus()).getPodIP()).toList();
//                    System.out.println(list);
//                }
//
//            } finally {
//                response.close();
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        } finally {
//            try {
//                httpClient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

//    public String escapeString(String str) {
//        try {
//            return URLEncoder.encode(str, "utf8").replaceAll("\\+", "%20");
//        } catch (UnsupportedEncodingException e) {
//            return str;
//        }
//    }


}
