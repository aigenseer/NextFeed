package com.nextfeed.library.core.enums;

import lombok.Getter;

public enum UserRole {
    PARTICIPANT("PARTICIPANT"),
    PRESENTER("PRESENTER");

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
