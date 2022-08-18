package com.nextfeed.library.socket.utils;

//import com.auth0.jwt.interfaces.Claim;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Builder;
import lombok.Getter;

import java.security.Principal;
import java.util.Map;

public class PrincipalUtils {

    @Builder
    public static class Claim {
        private int id;
        public int asInt(){
            return id;
        }
    }

    public static Claim getClaim(String value, Principal principal){
        return Claim.builder().id(1).build();
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
//        return ((Map<String, Claim>) token.getCredentials()).get(value);
    }


}
