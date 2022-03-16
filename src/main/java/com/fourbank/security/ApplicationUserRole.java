package com.fourbank.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    NORMAL(Sets.newHashSet(ApplicationUserPermition.NORMAL_READ, ApplicationUserPermition.NORMAL_WRITE)),
    EMPRESA(Sets.newHashSet(ApplicationUserPermition.EMPRESA_READ, ApplicationUserPermition.EMPRESA_WRITE)),
    ADMIN(Sets.newHashSet(ApplicationUserPermition.NORMAL_READ, ApplicationUserPermition.NORMAL_WRITE, ApplicationUserPermition.EMPRESA_READ, ApplicationUserPermition.EMPRESA_WRITE));

    private final Set<ApplicationUserPermition> permissions;

    ApplicationUserRole(Set<ApplicationUserPermition> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermition> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
