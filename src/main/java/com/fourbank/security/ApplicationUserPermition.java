package com.fourbank.security;

public enum ApplicationUserPermition {
    NORMAL_READ("normal:read"),
    NORMAL_WRITE("normal:write"),
    EMPRESA_READ("empresa:read"),
    EMPRESA_WRITE("empresa:write");

    private final String permission;

    ApplicationUserPermition(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
