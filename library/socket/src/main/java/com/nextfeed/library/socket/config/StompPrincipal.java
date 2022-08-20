package com.nextfeed.library.socket.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;
@AllArgsConstructor
public class StompPrincipal implements Principal {
    @Getter
    String name;

}
