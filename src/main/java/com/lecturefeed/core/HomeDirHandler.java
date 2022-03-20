package com.lecturefeed.core;

import com.lecturefeed.utils.PathUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HomeDirHandler {

    private final static String HOME_FOLDER = ".lectureFeed";

    public static Path getLectureFeedPath(){
        return Paths.get(PathUtils.getUserHomePath().toString(), HOME_FOLDER);
    }

    public static Path getSafetyLectureFeedPath(){
        createHomeDir();
        return getLectureFeedPath();
    }

    public static void createHomeDir(){
        try{
            if (Files.notExists(getLectureFeedPath())) {
                new File(getLectureFeedPath().toString()).mkdirs();
            }
        }catch (Exception e){
            throw new HomeDirHandlerRuntimeException(String.format("Can not create home dir %s", getLectureFeedPath()));
        }
    }

    static class HomeDirHandlerRuntimeException extends RuntimeException{
        public HomeDirHandlerRuntimeException(String s){
            super(s);
        }
    }
}
