package com.nextfeed.service.core.session.ports.incoming.authorization;

import com.nextfeed.library.core.proto.manager.ValidateUserRequest;
import com.nextfeed.service.core.session.core.authorization.JwtResponse;
import com.nextfeed.service.core.session.core.authorization.LoginParticipantRequest;
import com.nextfeed.service.core.session.core.authorization.RegistrationRequest;


public interface IAuthorizationService {
    JwtResponse testPresenterAuthentication();
    JwtResponse presenterAuthentication(ValidateUserRequest request);
    JwtResponse presenterRegistration(RegistrationRequest request);
    JwtResponse participantAuthentication(LoginParticipantRequest request);
}
