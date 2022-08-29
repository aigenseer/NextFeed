package com.nextfeed.library.core.service.manager.dto.system;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewConfigurationRequest {
    private String name;
    private String value;
}
