package com.nextfeed.service.core.session.core.authorization;

import com.nextfeed.library.core.proto.manager.ValidateUserRequest;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.library.core.valueobject.user.UserValue;
import com.nextfeed.library.security.TokenUserService;
import com.nextfeed.service.core.session.ports.incoming.authorization.IAuthorizationService;
import com.nextfeed.service.core.session.ports.incoming.session.ISessionManager;
import com.nextfeed.service.core.session.ports.incoming.usermanagement.IParticipantManager;
import com.nextfeed.service.core.session.ports.incoming.usermanagement.IUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class AuthorizationService implements IAuthorizationService {

    private final TokenUserService tokenUserService;
    private final ServiceUtils serviceUtils;
    private final ISessionManager sessionManager;
    private final IParticipantManager participantManager;
    private final IUserManager userManager;

    public JwtResponse testPresenterAuthentication(){
        var optionalUserValue = userManager.getUserByMailAddress("ok@ok.de");
        UserValue userValue;
        if(!optionalUserValue.isPresent()){
            userValue = userManager.createUser("ok@ok.de", "OK", "root");
        }else{
            userValue = optionalUserValue.get();
        }
        return new JwtResponse(tokenUserService.getTokenByPresenterUser(userValue.getEntity().getName(), userValue.getEntity().getHashPassword()));
    }

    public JwtResponse presenterAuthentication(ValidateUserRequest request){
        if(!userManager.validatePasswordByMailAddress(request.getMailAddress(), request.getPw())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mail-Address or password wrong");
        }
        var userValue = userManager.getUserByMailAddress(request.getMailAddress()).get();
        return new JwtResponse(tokenUserService.getTokenByPresenterUser(userValue.getEntity().getName(), userValue.getEntity().getHashPassword()));
    }

    public JwtResponse presenterRegistration(RegistrationRequest request){
        if(userManager.getUserByMailAddress(request.getMailAddress()).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Mail-Address are exists");
        }
        var userValue = userManager.createUser(request.getName(), request.getMailAddress(), request.getPw());
        return new JwtResponse(tokenUserService.getTokenByPresenterUser(userValue.getEntity().getName(), userValue.getEntity().getHashPassword()));
    }

    private boolean isCorrectSessionCode(Integer sessionId, String sessionCode){
        var session = sessionManager.getSessionById(sessionId);
        return session.isPresent() && session.get().getEntity().getSessionCode().equals(sessionCode);
    }

    public JwtResponse participantAuthentication(LoginParticipantRequest request){
        serviceUtils.checkSessionId(request.getSessionId());
        if(!isCorrectSessionCode(request.getSessionId(), request.getSessionCode())) throw new BadCredentialsException("Bad session data");
        var participantValue = participantManager.createParticipantBySessionId(request.getSessionId(), request.getNickname());
        return new JwtResponse(tokenUserService.getTokenBytParticipant(participantValue.getEntity()));
    }

}
