package com.nextfeed.library.core.utils;

public class ObjectUtils {

    public static <T> T getValueOrDefault(T value, T defaultValue){
        return value != null? value: defaultValue;
    }

}