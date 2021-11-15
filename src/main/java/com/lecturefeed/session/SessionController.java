package com.lecturefeed.session;

import lombok.Data;

import java.util.HashMap;

@Data
public class SessionController {
    private final HashMap<Integer,Session> sessions;

    public Session createSession(){
        return new Session(sessions.size());
    }
}
