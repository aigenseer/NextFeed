package com.nextfeed.service.generic.authorization.ports.incoming;

import com.nextfeed.library.core.proto.manager.ValidateUserRequest;
import com.nextfeed.service.generic.authorization.core.dto.LoginParticipantRequest;
import com.nextfeed.service.generic.authorization.core.dto.RegistrationRequest;
import com.nextfeed.service.generic.authorization.core.dto.JwtResponse;

public interface IAuthorizationService {

    JwtResponse testPresenterAuthentication();
    JwtResponse presenterAuthentication(ValidateUserRequest request);
    JwtResponse presenterRegistration(RegistrationRequest request);
    JwtResponse participantAuthentication(LoginParticipantRequest request);
}
