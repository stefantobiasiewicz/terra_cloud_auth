package pl.gekoniarze.auth.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.gekoniarze.auth.repository.UserRole;

import java.time.Instant;
import java.util.Collection;

@Builder
@Getter
public class AppUser implements UserDetails {
    private Integer id;
    private String firstName;
    private String surname;
    private String email;
    private String password;
    private Instant createdOn;
    private boolean deletedFlag;
    private boolean isAccountExpired;
    private boolean isAccountLocked;
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !deletedFlag;
    }
}
