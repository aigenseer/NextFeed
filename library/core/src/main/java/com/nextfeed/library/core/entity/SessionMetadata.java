package com.nextfeed.library.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionMetadata {
    private int sessionId;
    private String name;
    private long closed;
}
