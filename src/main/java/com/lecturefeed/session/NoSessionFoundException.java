package com.lecturefeed.session;

public class NoSessionFoundException extends RuntimeException{

    public NoSessionFoundException()
    {
        super("There is no Session with the given id.");
    }

    public NoSessionFoundException(int id)
    {
        super("There is no Session with id "+id);
    }
    public NoSessionFoundException(String message) {
        super(message);
    }

    public NoSessionFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSessionFoundException(Throwable cause) {
        super(cause);
    }

    public NoSessionFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
