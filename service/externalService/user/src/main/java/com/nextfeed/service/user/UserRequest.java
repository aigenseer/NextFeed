package com.nextfeed.service.user;

public record UserRequest(String name) {
    public String name() {
        return name;
    }
}
