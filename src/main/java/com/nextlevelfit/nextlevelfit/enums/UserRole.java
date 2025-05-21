package com.nextlevelfit.nextlevelfit.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_USER, ROL_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
