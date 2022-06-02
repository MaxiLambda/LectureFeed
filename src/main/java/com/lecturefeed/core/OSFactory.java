package com.lecturefeed.core;

import com.lecturefeed.core.os.IOSSystem;
import com.lecturefeed.core.os.WindowsSystem;

public class OSFactory {
    private static final String OS = System.getProperty("os.name").toLowerCase();

    private OSFactory(){}

    public static IOSSystem getOSSystem(){
        if (isWindows()) {
            return new WindowsSystem();
        } else if (isMac()) {
            throw new RuntimeException("MacOS are not supported");
        } else if (isUnix()) {
            throw new RuntimeException("Unix or Linux are not supported");
        } else if (isSolaris()) {
            throw new RuntimeException("Solaris are not supported");
        }
        throw new RuntimeException("Your OS are not supported");
    }

    public static boolean isWindows() {
        return OS.contains("win");
    }

    public static boolean isMac() {
        return OS.contains("mac");
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    public static boolean isSolaris() {
        return OS.contains("sunos");
    }

    public static String getOS(){
        if (isWindows()) {
            return "win";
        } else if (isMac()) {
            return "osx";
        } else if (isUnix()) {
            return "uni";
        } else if (isSolaris()) {
            return "sol";
        } else {
            return "err";
        }
    }

}
