package pl.gekoniarze.auth.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pl.gekoniarze.auth.model.FailedLogin;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "failed_login_attemps")
public class FailedLoginAttemptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NonNull String username;
    @NonNull Instant attemptDate;

    public FailedLogin toFailedLogin(){
        return new FailedLogin(
            username,
            attemptDate
        );
    }
}
