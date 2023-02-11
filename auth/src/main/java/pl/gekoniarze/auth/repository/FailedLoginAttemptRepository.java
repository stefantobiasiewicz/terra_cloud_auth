package pl.gekoniarze.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedLoginAttemptRepository extends JpaRepository<FailedLoginAttemptEntity, Integer> {
}
