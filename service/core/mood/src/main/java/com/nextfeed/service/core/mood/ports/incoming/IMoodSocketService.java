package com.nextfeed.service.core.mood.ports.incoming;

public interface IMoodSocketService {
    void sendMood(int sessionId, double value);
}
