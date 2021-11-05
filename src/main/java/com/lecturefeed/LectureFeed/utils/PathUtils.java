package com.lecturefeed.LectureFeed.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtils {

    public static Path getUserHomePath(){
        return Paths.get(System.getProperty("user.home"));
    }

}
