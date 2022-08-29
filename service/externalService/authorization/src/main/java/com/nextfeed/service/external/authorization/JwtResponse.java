package com.nextfeed.service.external.authorization;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse{
    private String token;
}