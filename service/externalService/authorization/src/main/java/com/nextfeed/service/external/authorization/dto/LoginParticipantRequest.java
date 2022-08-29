package com.nextfeed.service.external.authorization.dto;

import lombok.Data;

@Data
public class LoginParticipantRequest {
    private int sessionId;
    private String nickname;
    private String sessionCode;
}
