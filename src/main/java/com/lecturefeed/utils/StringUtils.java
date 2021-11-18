package com.lecturefeed.utils;

import java.security.SecureRandom;
import java.util.stream.Collectors;


public class StringUtils {
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom rnd = new SecureRandom();

    public static String randomString(int len){
        return len < 1 ?
                "" : rnd.ints(0,CHARS.length()).
                limit(len).
                mapToObj(CHARS::charAt).
                map(Object::toString).
                collect(Collectors.joining());
    }
}
