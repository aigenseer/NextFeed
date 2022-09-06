package com.nextfeed.service.external.authorization;


import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.entity.User;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.library.core.service.manager.ParticipantManagerService;
import com.nextfeed.library.core.service.manager.SessionManagerService;
import com.nextfeed.library.core.service.manager.UserManagerService;
import com.nextfeed.library.core.service.manager.dto.user.NewUserRequest;
import com.nextfeed.library.core.service.manager.dto.user.ValidateUserRequest;
import com.nextfeed.library.security.JWTTokenService;
import com.nextfeed.library.security.JwtUserDetailsService;
import com.nextfeed.library.security.TokenUserService;
import com.nextfeed.service.external.authorization.dto.LoginParticipantRequest;
import com.nextfeed.service.external.authorization.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;

@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/authorization-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorizationRestController {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationRestController.class, args);
    }

    private final UserManagerService userManagerService;
    private final TokenUserService tokenUserService;
    private final ServiceUtils serviceUtils;
    private final SessionManagerService sessionManagerService;
    private final ParticipantManagerService participantManagerService;

    @RequestMapping(value = "/v1/test/auth", method = RequestMethod.GET)
    public JwtResponse testPresenterAuthentication(){
        User user = userManagerService.getUserByMailAddress("ok@ok.de");
        if(user == null){
            user = userManagerService.createUser(NewUserRequest.builder().mailAddress("ok@ok.de").name("OK").pw("root").build());
        }
        return new JwtResponse(tokenUserService.getTokenByPresenterUser(user));
    }

    @RequestMapping(value = "/v1/presenter/auth", method = RequestMethod.POST)
    public JwtResponse presenterAuthentication(@RequestBody ValidateUserRequest request){
        if(!userManagerService.validatePasswordByMailAddress(request)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mail-Address or password wrong");
        }
        User user = userManagerService.getUserByMailAddress(request.getMailAddress());
        return new JwtResponse(tokenUserService.getTokenByPresenterUser(user));
    }

    @RequestMapping(value = "/v1/presenter/registration", method = RequestMethod.POST)
    public JwtResponse presenterRegistration(@RequestBody RegistrationRequest request){
        if(userManagerService.getUserByMailAddress(request.getMailAddress()) != null){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Mail-Address are exists");
        }
        User user = userManagerService.createUser(NewUserRequest.builder().mailAddress(request.getMailAddress()).name(request.getName()).pw(request.getPw()).build());
        return new JwtResponse(tokenUserService.getTokenByPresenterUser(user));
    }

    private boolean isCorrectSessionCode(Integer sessionId, String sessionCode){
        Session session = sessionManagerService.getSessionById(sessionId);
        return session != null && session.getSessionCode().equals(sessionCode);
    }

    @RequestMapping(value = "/v1/participant/auth", method = RequestMethod.POST)
    public JwtResponse participantAuthentication(@RequestBody LoginParticipantRequest request){
        serviceUtils.checkSessionId(request.getSessionId());
        if(!isCorrectSessionCode(request.getSessionId(), request.getSessionCode())) throw new BadCredentialsException("Bad session data");
        Participant participant = participantManagerService.createParticipantBySessionId(request.getSessionId(), Participant.builder().nickname(request.getNickname()).build());
        return new JwtResponse(tokenUserService.getTokenBytParticipant(participant));
    }

}
