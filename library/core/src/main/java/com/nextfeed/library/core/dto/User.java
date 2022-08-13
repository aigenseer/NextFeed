package com.nextfeed.library.core.dto;

public record User(String name) {
    public String name() {
        return name;
    }
}
