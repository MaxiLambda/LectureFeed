package com.lecturefeed.utils;

import com.lecturefeed.authentication.AdminSecurityConfigAdapter;

import java.util.Arrays;
import java.util.HashMap;

public class RunTimeParameterUtils {

    private static final String[][] DEFAULT_ARGS = {
            {"debug","false"}
    };

    public static HashMap<String, String> getDefaultRunTimeArgs(){
        HashMap<String, String> args = new HashMap<>();
        Arrays.stream(DEFAULT_ARGS).parallel().forEach(value -> args.put(value[0],value[1]));
        return args;
    }

    public static HashMap<String, String> getModifiedRunTimeArgs(String[] args){
        HashMap<String, String> modifiedArgs = getDefaultRunTimeArgs();
        for (int i = 0; i < args.length-1; i++) {
            if(args[i].matches("^-.*$")){
                modifiedArgs.put(args[i].substring(1),args[i+1]);
            }
        }
        return modifiedArgs;
    }

    public static void setRunTimeArgs(String[] args){
        HashMap<String, String> runTimeArgs = getModifiedRunTimeArgs(args);

        AdminSecurityConfigAdapter.setInDebugMode(Boolean.parseBoolean(runTimeArgs.get("debug")));
    }

}
