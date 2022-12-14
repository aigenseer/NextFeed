package com.nextfeed.library.core.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtils {

    private PathUtils(){}

    public static Path getUserHomePath(){
        return Paths.get(System.getProperty("user.home"));
    }

}
