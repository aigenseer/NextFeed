package com.nextfeed.library.security.utils;

//import com.auth0.jwt.interfaces.Claim;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import com.nextfeed.library.security.JWTTokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PrincipalUtils {

    private final JWTTokenService tokenService;

    public String getTokenByPrincipal(Principal principal){
        return  (String) ((UsernamePasswordAuthenticationToken) principal).getCredentials();
    }

    public Claims getClaims(Principal principal){
        return this.tokenService.getAllClaimsFromToken(getTokenByPrincipal(principal));
    }

    public <T> Optional<T> getClaim(String key, Principal principal){
        return this.tokenService.getClaimFromToken(getTokenByPrincipal(principal), key);
    }


}
