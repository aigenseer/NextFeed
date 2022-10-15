package com.nextfeed.service.external.authorization;

import com.nextfeed.library.core.grpc.service.manager.ParticipantManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.SessionManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.UserManagerServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.security.TokenUserService;
import com.nextfeed.service.external.authorization.dto.LoginParticipantRequest;
import com.nextfeed.service.external.authorization.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/authorization-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorizationRestController {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationRestController.class, args);
    }

    private final TokenUserService tokenUserService;
    private final ServiceUtils serviceUtils;
    private final SessionManagerServiceClient sessionManagerServiceClient;
    private final ParticipantManagerServiceClient participantManagerService;
    private final UserManagerServiceClient userManagerServiceClient;

    @RequestMapping(value = "/v1/test/auth", method = RequestMethod.GET)
    public JwtResponse testPresenterAuthentication(){
        var userDTO = userManagerServiceClient.getUserByMailAddress("ok@ok.de");
        if(userDTO.isPresent()){
            userDTO = Optional.of(userManagerServiceClient.createUser("ok@ok.de", "OK", "root"));
        }
        return new JwtResponse(tokenUserService.getTokenByPresenterUser(userDTO.get().getName(), userDTO.get().getHashPassword()));
    }


    @RequestMapping(value = "/v1/presenter/auth", method = RequestMethod.POST)
    public JwtResponse presenterAuthentication(@RequestBody ValidateUserRequest request){
        if(!userManagerServiceClient.validatePasswordByMailAddress(request.getMailAddress(), request.getPw())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mail-Address or password wrong");
        }
        DTOEntities.UserDTO userDTO = userManagerServiceClient.getUserByMailAddress(request.getMailAddress()).get();
        return new JwtResponse(tokenUserService.getTokenByPresenterUser(userDTO.getName(), userDTO.getHashPassword()));
    }

    @RequestMapping(value = "/v1/presenter/registration", method = RequestMethod.POST)
    public JwtResponse presenterRegistration(@RequestBody RegistrationRequest request){
        if(userManagerServiceClient.getUserByMailAddress(request.getMailAddress()).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Mail-Address are exists");
        }
        DTOEntities.UserDTO userDTO = userManagerServiceClient.createUser(request.getName(), request.getMailAddress(), request.getPw());
        return new JwtResponse(tokenUserService.getTokenByPresenterUser(userDTO.getName(), userDTO.getHashPassword()));
    }

    private boolean isCorrectSessionCode(Integer sessionId, String sessionCode){
        var session = sessionManagerServiceClient.getSessionById(sessionId);
        return session.isPresent() && session.get().getSessionCode().equals(sessionCode);
    }

    @RequestMapping(value = "/v1/participant/auth", method = RequestMethod.POST)
    public JwtResponse participantAuthentication(@RequestBody LoginParticipantRequest request){
        serviceUtils.checkSessionId(request.getSessionId());
        if(!isCorrectSessionCode(request.getSessionId(), request.getSessionCode())) throw new BadCredentialsException("Bad session data");
        var dto = participantManagerService.createParticipantBySessionId(request.getSessionId(), DTOEntities.ParticipantDTO.newBuilder().setNickname(request.getNickname()).build());
        var participant = DTO2EntityUtils.dto2Participant(dto);
        return new JwtResponse(tokenUserService.getTokenBytParticipant(participant));
    }

}
