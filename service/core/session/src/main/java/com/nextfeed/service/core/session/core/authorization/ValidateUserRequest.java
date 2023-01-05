package com.nextfeed.service.core.session.core.authorization;

import lombok.Data;

@Data
public class ValidateUserRequest {
    private String mailAddress;
    private String pw;
}
