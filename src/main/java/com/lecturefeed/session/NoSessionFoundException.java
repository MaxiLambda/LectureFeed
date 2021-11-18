package com.lecturefeed.session;

public class NoSessionFoundException extends RuntimeException{

    public NoSessionFoundException()
    {
        super("There is no Session with the given id.");
    }
}
