package com.nextfeed.library.core.utils;

public class IntegerUtils {

    public static int getRandomIntegerByRange(int min, int max){
        return (int) (Math.random() * (max - min)) + min;
    }

}
