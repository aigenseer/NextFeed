package com.nextfeed.library.core.service.dto.manager.mood;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCalculatedMoodRequest {
    private Integer moodValue;
    private Integer participantId;
}
