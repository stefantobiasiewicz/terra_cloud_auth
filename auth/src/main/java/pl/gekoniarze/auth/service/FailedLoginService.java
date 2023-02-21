package pl.gekoniarze.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.gekoniarze.auth.model.FailedLogin;
import pl.gekoniarze.auth.repository.FailedLoginAttemptEntity;
import pl.gekoniarze.auth.repository.FailedLoginAttemptRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FailedLoginService {
    private final FailedLoginAttemptRepository repository;

    public void saveFailedLoginAttempt(String username) {
        repository.save(FailedLoginAttemptEntity.builder().username(username).attemptDate(Instant.now()).build());
    }

    public List<FailedLogin> fetchAll(String username, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("username"));
        final var failedLoginsEntities = repository.findAll(page);

        if(username == null) {
            return failedLoginsEntities.get().map(FailedLoginAttemptEntity::toFailedLogin).toList();
        }
        else {
            return failedLoginsEntities.get()
                    .map(FailedLoginAttemptEntity::toFailedLogin)
                    .toList()
                    .stream()
                    .filter(failedLogin -> failedLogin.getUsername().equals(username))
                    .collect(Collectors.toList());
        }
    }
}
