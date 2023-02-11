package pl.gekoniarze.auth.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gekoniarze.auth.model.AppUser;
import pl.gekoniarze.auth.repository.UserRole;
import pl.gekoniarze.auth.rest.model.AuthenticationRequest;
import pl.gekoniarze.auth.rest.model.AuthenticationResponse;
import pl.gekoniarze.auth.rest.model.RegisterRequest;
import pl.gekoniarze.auth.service.AuthenticationService;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        final var user = AppUser.builder()
            .firstName(request.getFirstName())
            .surname(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(UserRole.USER)
            .createdOn(Instant.now())
            .build();

        final var jwt = authenticationService.register(user);

        return AuthenticationResponse.builder()
            .token(jwt)
            .build();
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        final var jwt = authenticationService.authenticate(request.getEmail(), request.getPassword());

        return AuthenticationResponse.builder()
            .token(jwt)
            .build();
    }
}
