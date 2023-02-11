package pl.gekoniarze.auth.repository;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum UserRole {
    GUEST(new HashSet<>(List.of(UserPermission.READ))),
    USER(new HashSet<>(List.of(UserPermission.READ,UserPermission.WRITE))),
    ADMIN(new HashSet<>(List.of(UserPermission.ADMIN, UserPermission.FAILED_LOGIN)));


    Set<UserPermission> permissions;

    public Set<GrantedAuthority> getAuthorities() {
        return this.permissions.stream()
            .map(
                authority ->
                    new SimpleGrantedAuthority(authority.name())
            )
            .collect(Collectors.toSet());
    }
}
