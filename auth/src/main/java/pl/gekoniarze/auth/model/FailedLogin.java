package pl.gekoniarze.auth.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.Instant;

@Value
@AllArgsConstructor
public class FailedLogin {
    String username;
    Instant date;
}
