package com.nextfeed.service.core.session.core.authorization;

import lombok.Data;

@Data
public class LoginParticipantRequest {
    private int sessionId;
    private String nickname;
    private String sessionCode;
}
