package com.nextfeed.service.core.mood.ports.incoming;

import com.nextfeed.library.core.valueobject.mood.MoodValue;
import com.nextfeed.library.core.valueobject.mood.MoodValueList;

public interface IMoodManager {

    MoodValue createCalculatedMoodValue(int sessionId, double moodValue, int participantId);

    MoodValueList findBySessionId(int sessionId);

}
