package com.nextfeed.service.core.session.core.authorization;

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