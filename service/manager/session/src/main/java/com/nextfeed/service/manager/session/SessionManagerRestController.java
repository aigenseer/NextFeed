package com.nextfeed.service.manager.session;


import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.SessionManagerService;
import com.nextfeed.library.core.service.dto.manager.session.NewSessionRequest;
import lombok.AllArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.bind.annotation.*;


@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories("com.nextfeed.library.manager.repository")
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RefreshScope
@AllArgsConstructor
@RestController
@RequestMapping(value = "/session-manager")
public class SessionManagerRestController implements SessionManagerService {

    public static void main(String[] args) {
        SpringApplication.run(SessionManagerRestController.class, args);
    }

    private final SessionManager sessionManager;



    @PostMapping("/v1/session/create")
    public Session createSession(@RequestBody NewSessionRequest request){
        return sessionManager.createSession(request.getName());
    }

    @GetMapping("/v1/session/{sessionId}")
    public Session getSessionById(@PathVariable("sessionId") Integer sessionId){
        return sessionManager.getSessionById(sessionId);
    }

    @PostMapping("/v1/session/save")
    public Session saveSession(@RequestBody Session session){
        return sessionManager.saveSession(session);
    }

}
