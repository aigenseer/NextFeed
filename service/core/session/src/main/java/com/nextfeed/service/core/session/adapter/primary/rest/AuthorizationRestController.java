package com.nextfeed.service.core.session.adapter.primary.rest;

import com.nextfeed.library.core.proto.manager.ValidateUserRequest;
import com.nextfeed.service.core.session.core.authorization.JwtResponse;
import com.nextfeed.service.core.session.core.authorization.LoginParticipantRequest;
import com.nextfeed.service.core.session.core.authorization.RegistrationRequest;
import com.nextfeed.service.core.session.ports.incoming.authorization.IAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/authorization-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorizationRestController {

    private final IAuthorizationService authorizationService;

    @RequestMapping(value = "/v1/test/auth", method = RequestMethod.GET)
    public JwtResponse testPresenterAuthentication() {
        return authorizationService.testPresenterAuthentication();
    }

    @RequestMapping(value = "/v1/presenter/auth", method = RequestMethod.POST)
    public JwtResponse presenterAuthentication(@RequestBody ValidateUserRequest request) {
        return authorizationService.presenterAuthentication(request);
    }

    @RequestMapping(value = "/v1/presenter/registration", method = RequestMethod.POST)
    public JwtResponse presenterRegistration(@RequestBody RegistrationRequest request) {
        return authorizationService.presenterRegistration(request);
    }

    @RequestMapping(value = "/v1/participant/auth", method = RequestMethod.POST)
    public JwtResponse participantAuthentication(@RequestBody LoginParticipantRequest request) {
        return authorizationService.participantAuthentication(request);
    }

}
