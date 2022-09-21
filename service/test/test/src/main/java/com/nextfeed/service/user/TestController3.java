package com.nextfeed.service.user;

import com.google.gson.reflect.TypeToken;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.auth.ApiKeyAuth;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Objects;

import static org.apache.http.conn.ssl.SSLConnectionSocketFactory.SSL;

//@SpringBootApplication(scanBasePackages = "com.nextfeed")
public class TestController3 {

    public static void main(String[] args) {
//        SpringApplication.run(TestController3.class, args);
    }

    private final static String TOKEN_FILE_PATH = "/var/run/secrets/kubernetes.io/serviceaccount/token";
    private final static String CA_FILE_PATH = "/var/run/secrets/kubernetes.io/serviceaccount/ca.crt";
    private final static String NAMESPACE = "nextfeed";
    private final static String POD_LABEL_KEY = "io.kompose.service";

    @PostConstruct
    public void init(){
        var pods = getPodIpsByServiceName("system-manager-service");
        System.out.println(pods);
    }

    public List<String> getPodIpsByServiceName(String serviceName){
        try {
            File f = new File(TOKEN_FILE_PATH);
            if(!f.exists() || f.isDirectory()) throw new RuntimeException("Token file not found");
            String token = readFile(TOKEN_FILE_PATH);
            if(token == null) throw new RuntimeException("Token content empty");

            File caFile = new File(CA_FILE_PATH);
            if(!caFile.exists() || caFile.isDirectory()) throw new RuntimeException("CA file not found");
            ApiClient defaultClient = Config.defaultClient();

//            final SSLContext sslContext = SSLContext.getInstance(SSL);
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
//            final OkHttpClient httpclient = builder.build();

//            ApiClient defaultClient = Configuration.getDefaultApiClient();
//            defaultClient.setBasePath("https://kubernetes.default.svc");
//            defaultClient.setHttpClient(httpclient);


//            Type localVarReturnType = (new TypeToken<V1PodList>() {
//            }).getType();

            defaultClient.setBasePath("http://kubernetes.default.svc");
            defaultClient.setSslCaCert(new FileInputStream(caFile));
//            defaultClient.setApiKeyPrefix("");
            ApiKeyAuth BearerToken = (ApiKeyAuth) defaultClient.getAuthentication("BearerToken");
            BearerToken.setApiKey(token);
            defaultClient.setApiKeyPrefix("Bearer");

            BearerToken = (ApiKeyAuth) defaultClient.getAuthentication("BearerToken");
            System.out.println("ApiKeyPrefix: "+BearerToken.getApiKeyPrefix());
            System.out.println("getApiKey: "+BearerToken.getApiKey());
            System.out.println("BasePath: "+defaultClient.getBasePath());
            System.out.println("SslCaCert: "+defaultClient.getSslCaCert());

            Configuration.setDefaultApiClient(defaultClient);
            CoreV1Api api = new CoreV1Api();
            return api.listNamespacedPod(NAMESPACE, null, null, null, null, "%s=%s".formatted(POD_LABEL_KEY, serviceName), null, null, null, null, null).getItems().stream().map(p -> Objects.requireNonNull(p.getStatus()).getPodIP()).toList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


//    final TrustManager[] trustAllCerts = new TrustManager[]{
//            new X509TrustManager() {
//
//                @Override
//                public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
//                                               String authType) throws
//                        CertificateException {
//                }
//
//                @Override
//                public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
//                                               String authType) throws
//                        CertificateException {
//                }
//                @Override
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return new java.security.cert.X509Certificate[]{};
//                }
//            }
//    };



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






}
