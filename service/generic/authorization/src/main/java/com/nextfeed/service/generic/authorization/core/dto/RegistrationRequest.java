package com.nextfeed.service.generic.authorization.core.dto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String mailAddress;
    private String name;
    private String pw;
}
