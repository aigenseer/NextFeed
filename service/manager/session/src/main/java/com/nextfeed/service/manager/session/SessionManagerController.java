package com.nextfeed.service.manager.session;


import lombok.AllArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;




import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories("com.nextfeed.library.manager.repository")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RefreshScope
@AllArgsConstructor
public class SessionManagerController {

    public static void main(String[] args) {
        SpringApplication.run(SessionManagerController.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate){
//        return args -> {
//            kafkaTemplate.send("firsttopic", "geht doch");
//        };
//    }

//    private final SessionManager sessionManager;
//    private final SessionDBService sessionDBService;


    @Bean
    public Supplier<Message> producer() {
        return () -> new Message(" jack from Streams");
    }

    @Bean
    public Function<String, String> uppercase() {
        return value -> {
            System.out.println("Received: " + value);
            return value.toUpperCase()
        };
    }

    @Bean
    public Consumer<Message> consumer() {
        return message -> System.out.println("received " + message);
    }







}
