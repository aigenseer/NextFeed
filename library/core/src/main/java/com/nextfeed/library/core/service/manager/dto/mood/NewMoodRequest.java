package com.nextfeed.library.core.service.manager.dto.mood;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewMoodRequest {
    private Integer moodValue;
    private Integer participantsCount;
}
