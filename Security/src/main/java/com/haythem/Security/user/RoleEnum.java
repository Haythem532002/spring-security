package com.haythem.Security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum RoleEnum {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE,
                    Permission.MANAGMENT_READ,
                    Permission.MANAGMENT_CREATE,
                    Permission.MANAGMENT_UPDATE,
                    Permission.MANAGMENT_DELETE
            )
    ),
    MANAGER(
            Set.of(Permission.MANAGMENT_READ,
                    Permission.MANAGMENT_CREATE,
                    Permission.MANAGMENT_UPDATE,
                    Permission.MANAGMENT_DELETE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
