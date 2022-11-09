package com.nextfeed.service.core.mood.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.NewCalculatedMoodRequest;
import com.nextfeed.library.core.proto.manager.NewMoodRequest;

public interface IMoodManager {

    DTOEntities.MoodEntityDTO addMoodValueToSession(int sessionId, NewMoodRequest request);

    DTOEntities.MoodEntityDTO createCalculatedMoodValue(int sessionId, NewCalculatedMoodRequest request);

}
