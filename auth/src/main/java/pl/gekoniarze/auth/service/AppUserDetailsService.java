package pl.gekoniarze.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.gekoniarze.auth.model.AppUser;
import pl.gekoniarze.auth.repository.AppUserEntity;
import pl.gekoniarze.auth.repository.AuthUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return authUserRepository
            .findByEmail(username)
            .map(AppUserEntity::toAppUser)
            .orElseThrow(
                () -> new UsernameNotFoundException(
                    "Username %s not found".formatted(username)
                )
            );
    }
}
