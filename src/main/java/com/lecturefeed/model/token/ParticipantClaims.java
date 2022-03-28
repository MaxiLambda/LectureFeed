package com.lecturefeed.model.token;

public enum ParticipantClaims implements TokenClaim{
    USERNAME("username"),
    ROLE("role"),
    SESSION_ID("sessionId"),
    ID("id");

    private final String value;
    ParticipantClaims(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
