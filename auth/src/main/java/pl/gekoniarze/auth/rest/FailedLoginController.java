package pl.gekoniarze.auth.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.gekoniarze.auth.rest.model.FailedLoginAttemptResponse;
import pl.gekoniarze.auth.service.FailedLoginService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class FailedLoginController {
    private final FailedLoginService failedLoginService;

    @GetMapping("/failedLogins")
    @PreAuthorize("hasAuthority('FAILED_LOGIN')")
    public ResponseEntity<List<FailedLoginAttemptResponse>> getFailedAttempts(
        @RequestParam final String username,
        @RequestParam final Integer pageNumber,
        @RequestParam final Integer pageSize
    ) {

        final var failedLogins = failedLoginService.fetchAll(username, pageNumber, pageSize);
        final var failedLoginsResponse =
            failedLogins.stream()
                .map(
                    failedLogin -> new FailedLoginAttemptResponse(failedLogin.getUsername(), failedLogin.getDate())
                ).toList();

        return ResponseEntity.ok(failedLoginsResponse);
    }

}
