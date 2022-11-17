package com.nextfeed.service.generic.authorization.core;

import com.nextfeed.library.core.grpc.service.manager.ParticipantManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.SessionManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.UserManagerServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.ValidateUserRequest;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.security.TokenUserService;
import com.nextfeed.service.generic.authorization.core.dto.JwtResponse;
import com.nextfeed.service.generic.authorization.core.dto.LoginParticipantRequest;
import com.nextfeed.service.generic.authorization.core.dto.RegistrationRequest;
import com.nextfeed.service.generic.authorization.ports.incoming.IAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorizationService implements IAuthorizationService {

    private final TokenUserService tokenUserService;
    private final ServiceUtils serviceUtils;
    private final SessionManagerServiceClient sessionManagerServiceClient;
    private final ParticipantManagerServiceClient participantManagerService;
    private final UserManagerServiceClient userManagerServiceClient;

    public JwtResponse testPresenterAuthentication(){
        var userDTO = userManagerServiceClient.getUserByMailAddress("ok@ok.de");
        if(userDTO.isPresent()){
            userDTO = Optional.of(userManagerServiceClient.createUser("ok@ok.de", "OK", "root"));
        }
        return new JwtResponse(tokenUserService.getTokenByPresenterUser(userDTO.get().getName(), userDTO.get().getHashPassword()));
    }

    public JwtResponse presenterAuthentication(ValidateUserRequest request){
        if(!userManagerServiceClient.validatePasswordByMailAddress(request.getMailAddress(), request.getPw())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mail-Address or password wrong");
        }
        DTOEntities.UserDTO userDTO = userManagerServiceClient.getUserByMailAddress(request.getMailAddress()).get();
        return new JwtResponse(tokenUserService.getTokenByPresenterUser(userDTO.getName(), userDTO.getHashPassword()));
    }

    public JwtResponse presenterRegistration(RegistrationRequest request){
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

    public JwtResponse participantAuthentication(LoginParticipantRequest request){
        serviceUtils.checkSessionId(request.getSessionId());
        if(!isCorrectSessionCode(request.getSessionId(), request.getSessionCode())) throw new BadCredentialsException("Bad session data");
        var dto = participantManagerService.createParticipantBySessionId(request.getSessionId(), DTOEntities.ParticipantDTO.newBuilder().setNickname(request.getNickname()).build());
        var participant = DTO2EntityUtils.dto2Participant(dto);
        return new JwtResponse(tokenUserService.getTokenBytParticipant(participant));
    }

}