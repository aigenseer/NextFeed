package com.nextfeed.service.external.authorization;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtRequest{
    private String mailAddress;
    private String password;
}