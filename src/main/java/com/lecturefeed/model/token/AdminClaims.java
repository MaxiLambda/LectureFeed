package com.lecturefeed.model.token;

public enum AdminClaims implements TokenClaim {
    USERNAME("username"),
    ROLE("role"),
    EXPIRATION_DATE("expirationDate");

    private final String value;
    AdminClaims(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
