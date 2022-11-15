package com.nextfeed.service.generic.authorization.core.dto;

import lombok.Data;

@Data
public class LoginParticipantRequest {
    private int sessionId;
    private String nickname;
    private String sessionCode;
}
