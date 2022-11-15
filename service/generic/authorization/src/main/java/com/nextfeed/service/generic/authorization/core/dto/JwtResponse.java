package com.nextfeed.service.generic.authorization.core.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse{
    private String token;
}