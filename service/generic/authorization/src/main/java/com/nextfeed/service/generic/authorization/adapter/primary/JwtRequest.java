package com.nextfeed.service.generic.authorization.adapter.primary;

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