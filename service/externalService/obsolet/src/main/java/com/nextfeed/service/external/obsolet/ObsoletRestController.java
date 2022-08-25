package com.nextfeed.service.external.obsolet;


import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.entity.SessionMetadata;
import com.nextfeed.library.core.service.external.SessionService;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.library.core.service.manager.ParticipantManagerService;
import com.nextfeed.library.core.service.manager.QuestionManagerService;
import com.nextfeed.library.core.service.manager.SessionManagerService;
import com.nextfeed.library.core.service.manager.dto.question.NewQuestionRequest;
import com.nextfeed.library.core.service.manager.dto.session.NewSessionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@EnableHystrix
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/obsolet-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class ObsoletRestController{

    public static void main(String[] args) {
        SpringApplication.run(ObsoletRestController.class, args);
    }

    private final SessionManagerService sessionManagerService;
    private final ServiceUtils serviceUtils;
    private final ParticipantManagerService participantManagerService;
    private final TokenService tokenService;
//    private final TokenService tokenService;
//    private final InetAddressSecurityService inetAddressSecurityService;

    @GetMapping("/v1/environment/info")
    public EnvironmentInfo getEnvironmentInfo() {
        return new EnvironmentInfo("localhost");
    }

    @GetMapping("/v1/auth/admin")
    public TokenModel adminAuth() {
        return tokenService.createAdminToken();
    }


    @PostMapping("/v1/auth/participant")
    public Object participantAuth(@RequestBody ParticipantAuthRequestModel authRequestModel, HttpServletRequest request) {
//        if(!sessionManager.isCorrectSessionCode(authRequestModel.getSessionId(),authRequestModel.getSessionCode()) ||
//                inetAddressSecurityService.isInetAddressBlocked(HttpServletRequestUtils.getRemoteAddrByRequest(request)))
//            throw new BadCredentialsException("Bad session data");

        serviceUtils.checkSessionId(authRequestModel.getSessionId());
        Participant participant = participantManagerService.createParticipantBySessionId(authRequestModel.getSessionId(), Participant.builder().nickname(authRequestModel.getNickname()).build());
        //create token
        TokenModel tokenModel = tokenService.createParticipantToken(authRequestModel.getNickname(), UserRole.PARTICIPANT, authRequestModel.getSessionId(), participant.getId());
        int userId = tokenService.getTokenValue("id", tokenModel).asInt();

        return tokenModel;
    }

}
