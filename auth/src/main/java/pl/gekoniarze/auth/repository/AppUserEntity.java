package pl.gekoniarze.auth.repository;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pl.gekoniarze.auth.model.AppUser;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_user")
public class AppUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @NonNull
    private String firstName;

    @NonNull
    private String surname;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    @JsonIgnore
    private String password;

    @NonNull
    private Instant createdOn;

    private boolean deletedFlag;

    private boolean isAccountExpired;

    private boolean isAccountLocked;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public AppUser toAppUser() {
        return AppUser.builder()
            .id(id)
            .firstName(firstName)
            .surname(surname)
            .email(email)
            .password(password)
            .createdOn(createdOn)
            .deletedFlag(deletedFlag)
            .isAccountExpired(isAccountExpired)
            .isAccountLocked(isAccountLocked)
            .role(role)
            .build();
    }

    public static AppUserEntity fromAppUser(final AppUser appUser) {
        return AppUserEntity.builder()
            .firstName(appUser.getFirstName())
            .surname(appUser.getSurname())
            .email(appUser.getEmail())
            .password(appUser.getPassword())
            .createdOn(appUser.getCreatedOn())
            .isAccountExpired(appUser.isAccountExpired())
            .isAccountLocked(appUser.isAccountLocked())
            .role(appUser.getRole())
            .build();
    }
}
