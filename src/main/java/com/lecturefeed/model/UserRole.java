package com.lecturefeed.model;

import lombok.Getter;

public enum UserRole {
    PARTICIPANT("PARTICIPANT"),
    ADMINISTRATOR("ADMIN");

    @Getter
    final String role;

    UserRole(String role){
        this.role = role;
    }

    public static UserRole fromString(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.role.equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        return null;
    }
}
