package com.nextfeed.service.core.mood.ports.incoming;

import com.nextfeed.library.core.proto.manager.NewCalculatedMoodRequest;
import com.nextfeed.library.core.proto.manager.NewMoodRequest;
import com.nextfeed.library.core.valueobject.mood.MoodValue;
import com.nextfeed.library.core.valueobject.mood.MoodValueList;

public interface IMoodManager {

    MoodValue addMoodValueToSession(int sessionId, NewMoodRequest request);

    MoodValue createCalculatedMoodValue(int sessionId, NewCalculatedMoodRequest request);

    MoodValueList findBySessionId(int sessionId);

}
