package com.lecturefeed.model;

import lombok.Getter;

public enum UserRole {
    PARTICIPANT("PARTICIPANT"),
    ADMINISTRATOR("ADMIN");

    @Getter
    String role;

    UserRole(String role){
        this.role = role;
    }
}
