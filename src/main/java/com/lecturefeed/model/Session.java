package com.lecturefeed.model;

import lombok.Getter;

public class Session {

    @Getter
    private String sessionCode;
    private int sessionId;

    public Session(String sessionCode, int sessionId)
    {
        this.sessionCode = sessionCode;
        this.sessionId = sessionId;
    }
}
