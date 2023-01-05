package com.nextfeed.service.core.session.core.authorization;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String mailAddress;
    private String name;
    private String pw;
}
