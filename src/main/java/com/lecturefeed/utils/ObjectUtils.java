package com.lecturefeed.utils;

public class ObjectUtils {

    public static <T> T getValueOrDefault(T value, T defaultValue){
        return value != null? value: defaultValue;
    }

}