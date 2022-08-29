package com.nextfeed.library.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class PreAuthTokenHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final String authHeaderName = "Authorization";
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JWTTokenService jwtTokenUtil;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String authorizationToken = getAuthorizationTokenByRequest(request);
        if(authorizationToken != null){
            return getAuthenticatedByToken(authorizationToken, request);
        }
        return null;
    }


    @PostConstruct
    public void init() {
        this.setAuthenticationManager(authentication -> {
            if(authentication.getPrincipal() == null){
                authentication.setAuthenticated(authentication.getPrincipal() != null);
                return authentication;
            }
            return (Authentication) authentication.getPrincipal();
        });
    }

    private UsernamePasswordAuthenticationToken getAuthenticatedByToken(String authorizationToken, HttpServletRequest request){
        String username = null;
        try {
            username = jwtTokenUtil.getUsernameFromToken(authorizationToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtTokenUtil.validateToken(authorizationToken, username)) {
                UserDetails userDetails = jwtTokenUtil.getUserDetailsByToken(authorizationToken);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        return null;
    }

    private String getAuthorizationTokenByRequest(HttpServletRequest request){
        String authorization = request.getHeader(authHeaderName);
        if(authorization == null){
            authorization = getAuthorizationTokenByCookieRequest(request);
        }
        return authorization;
    }

    private String getAuthorizationTokenByCookieRequest(HttpServletRequest request){
        if(request.getCookies() == null) return null;
        List<String> authorizations = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(authHeaderName) && cookie.getValue().length() > 0)
                .map(Cookie::getValue).toList();
        return  authorizations.size() == 1? authorizations.get(0): null;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}
