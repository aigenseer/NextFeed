package com.nextfeed.library.core.service.manager.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {
    private String mailAddress;
    private String name;
    private String pw;
}
