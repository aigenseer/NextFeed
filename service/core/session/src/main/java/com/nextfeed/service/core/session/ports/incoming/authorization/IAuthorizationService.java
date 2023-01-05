package com.nextfeed.service.core.session.ports.incoming.authorization;

import com.nextfeed.service.core.session.core.authorization.JwtResponse;
import com.nextfeed.service.core.session.core.authorization.LoginParticipantRequest;
import com.nextfeed.service.core.session.core.authorization.RegistrationRequest;
import com.nextfeed.service.core.session.core.authorization.ValidateUserRequest;


public interface IAuthorizationService {
    JwtResponse testPresenterAuthentication();
    JwtResponse presenterAuthentication(ValidateUserRequest request);
    JwtResponse presenterRegistration(RegistrationRequest request);
    JwtResponse participantAuthentication(LoginParticipantRequest request);
}
