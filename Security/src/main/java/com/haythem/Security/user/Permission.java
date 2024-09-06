package com.haythem.Security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGMENT_READ("managment:read"),
    MANAGMENT_UPDATE("managment:update"),
    MANAGMENT_CREATE("managment:create"),
    MANAGMENT_DELETE("managment:delete");
    @Getter
    private final String permission;

}
