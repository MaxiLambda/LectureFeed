package com.lecturefeed.model;

public class SessionAlreadyExistsException extends Exception{

    public SessionAlreadyExistsException(int sessionId)
    {
        super("A session with id '" + sessionId + "' already exists.");
    }
}
