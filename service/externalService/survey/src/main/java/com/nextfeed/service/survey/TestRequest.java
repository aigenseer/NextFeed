package com.nextfeed.service.survey;

public record TestRequest(String test) {
    public String test() {
        return test;
    }
}
