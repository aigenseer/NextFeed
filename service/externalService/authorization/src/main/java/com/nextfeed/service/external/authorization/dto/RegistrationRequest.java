package com.nextfeed.service.external.authorization.dto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String mailAddress;
    private String name;
    private String pw;
}
