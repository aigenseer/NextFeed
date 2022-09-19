package com.nextfeed.service.user;

//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.ssl.SSLContextBuilder;
//import org.apache.http.util.EntityUtils;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.http.HttpHeaders;
//
//import javax.annotation.PostConstruct;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.stream.Collectors;

//@SpringBootApplication(scanBasePackages = "com.nextfeed")
public class TestController2 {

//    public static void main(String[] args) {
//        SpringApplication.run(TestController2.class, args);
//    }
//
//    @PostConstruct
//    public void init(){
//        String tokenFilePath = "/var/run/secrets/kubernetes.io/serviceaccount/token";
//        String caFilePath = "/var/run/secrets/kubernetes.io/serviceaccount/ca.crt";
//        System.out.println("??");
//        try {
//            File f = new File(tokenFilePath);
//            if(f.exists() && !f.isDirectory()) {
//                String token = readFile(tokenFilePath);
//                String ca = readFile(caFilePath);
//                call(token, ca);
//            }else{
//                System.out.println("File not exists "+ tokenFilePath);
//            }
//
//            List<File> files = Files.list(Paths.get("./"))
//                    .map(Path::toFile)
//                    .filter(File::isFile)
//                    .collect(Collectors.toList());
//
//            files.forEach(System.out::println);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String readFile(String filePath){
//        try
//        {
//            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
//            return new String (bytes);
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private void call(String token, String ca){
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
//
//            HttpGet httpGet = new HttpGet("https://kubernetes.default.svc/api");
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


}
