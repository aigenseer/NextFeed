package com.nextfeed.library.security;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import com.nextfeed.library.core.entity.SystemConfiguration;
import com.nextfeed.library.core.service.manager.SystemManagerService;
import com.nextfeed.library.core.service.manager.dto.system.GetConfigurationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Service
public class JWTTokenService {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private final SystemManagerService systemManagerService;

//    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void init() throws InterruptedException {
        SystemConfiguration configuration = null;
        for (int i = 0; i <= 60; i++) {
            try {
                i++;
                configuration = systemManagerService.get(new GetConfigurationRequest("jwt.secret"));
                secret = configuration.getValue();
                break;
            }catch (Exception e){
                System.out.println(e);
                Thread.sleep(5000);
            }
        }
        if(configuration == null){
            throw new RuntimeException("Can not call service");
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public <T> Optional<T> getClaimFromToken(String token, String key) {
        var claim = getAllClaimsFromToken(token).get(key);
        if (claim != null) return Optional.of((T) claim);
        System.out.printf("JWTTokenService:: Key %s not found in token%n", key);
        return Optional.empty();
    }

    private Claims getBodyOfToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private boolean isValidate(String token){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !isTokenExpired(token);
        }catch (Exception e){
            return false;
        }
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateTokenWithAuthorities(UserDetails userDetails) {
        return generateTokenWithAuthorities(userDetails, new HashMap<>());
    }

    public String generateTokenWithAuthorities(UserDetails userDetails, Map<String, Object> claims) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority authority: userDetails.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }
        claims.put("authorities", authorities);
        return generateToken(claims, userDetails.getUsername());
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token, String username) {
        if(!isValidate(token)) return false;
        return getUsernameFromToken(token).equals(username);
    }

    public UserDetails getUserDetailsByToken(String token){
        if(!isValidate(token)) return null;
        ArrayList<String> authorities = (ArrayList<String>) getBodyOfToken(token).get("authorities");
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream().map(SimpleGrantedAuthority::new).toList();
        return new User(getClaimFromToken(token, Claims::getSubject), "", simpleGrantedAuthorities);
    }

    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationTokenByToken(String token){
        if(!isValidate(token)) return null;
        UserDetails userDetails = getUserDetailsByToken(token);
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

}
