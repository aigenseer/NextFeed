package com.nextfeed.library.security;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TokenUserService {

    private final JWTTokenService tokenService;

    public String getTokenByPresenterUser(User user){
        return tokenService.generateTokenWithAuthorities(getPresenterDetails(user));
    }

    public String getTokenBytParticipant(Participant participant){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", participant.getId());
        claims.put("username", participant.getNickname());
        return tokenService.generateTokenWithAuthorities(getParticipantDetails(participant), claims);
    }

    private UserDetails getPresenterDetails(User user){
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getHashPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+ UserRole.PRESENTER)));
    }

    private UserDetails getParticipantDetails(Participant participant){
        return new org.springframework.security.core.userdetails.User(participant.getNickname(), "", List.of(new SimpleGrantedAuthority("ROLE_"+ UserRole.PARTICIPANT)));
    }

}
