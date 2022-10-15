package com.nextfeed.library.core.service.external.dto.authorization;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewSessionRequest {
    private String name;
}
