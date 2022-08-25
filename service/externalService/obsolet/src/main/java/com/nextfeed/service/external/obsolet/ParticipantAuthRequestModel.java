package com.nextfeed.service.external.obsolet;

import lombok.Data;

@Data
public class ParticipantAuthRequestModel {
    private int sessionId;
    private String nickname;
    private String sessionCode;
}
