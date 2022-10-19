package com.example.commonsmodule.security.enums;

public enum UserRole {
    ROLE_STUDENT("ROLE_STUDENT"), ROLE_TUTOR("ROLE_TUTOR");
    private String value;

    UserRole(String value) {this.value = value;}
    public String getValue() {
        return value;
    }
}
