package com.lecturefeed.LectureFeed.core;

import com.lecturefeed.LectureFeed.utils.PathUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HomeDirHandler {

    private final static String HOME_FOLDER = ".lectureFeed";

    protected Path getLectureFeedPath(){
        return Paths.get(PathUtils.getUserHomePath().toString(), HOME_FOLDER);
    }

    protected void createHomeDir(){
        try{
            if (Files.notExists(getLectureFeedPath())) {
                new File(getLectureFeedPath().toString()).mkdirs();
            }
        }catch (Exception e){
            throw new RuntimeException(String.format("Can not create home dir %s", getLectureFeedPath()));
        }
    }

}
