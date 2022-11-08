package com.nextfeed.service.generic.authorization.adapter.primary.dto;

import lombok.Data;

@Data
public class LoginParticipantRequest {
    private int sessionId;
    private String nickname;
    private String sessionCode;
}
