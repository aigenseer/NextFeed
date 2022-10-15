package com.nextfeed.library.core.service.external.dto.authorization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewQuestionRequest {
    private Integer participantId;
    private Boolean anonymous;
    private String message;
    private Long created;
}
