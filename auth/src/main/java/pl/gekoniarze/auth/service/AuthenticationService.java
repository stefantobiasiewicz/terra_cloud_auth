package pl.gekoniarze.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.gekoniarze.auth.model.AppUser;
import pl.gekoniarze.auth.repository.AppUserEntity;
import pl.gekoniarze.auth.repository.AuthUserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthUserRepository repository;
    private final AppUserDetailsService appUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final FailedLoginService failedLoginService;

    public String register(AppUser user) {
        repository.save(AppUserEntity.fromAppUser(user));

        return jwtService.generateToken(user);
    }

    public String authenticate(String email, String password) {
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    email,
                    password
                )
            );
        }
        catch (BadCredentialsException e){
            failedLoginService.saveFailedLoginAttempt(email);
        }

        final var userDetails = appUserDetailsService.loadUserByUsername(email);
        return jwtService.generateToken(userDetails);
    }

}
