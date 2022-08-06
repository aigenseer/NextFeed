package com.nextfeed.library.dto;

public record User(String name) {
    public String name() {
        return name;
    }
}
