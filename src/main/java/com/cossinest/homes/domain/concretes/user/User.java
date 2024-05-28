package com.cossinest.homes.domain.concretes.user;


import com.cossinest.homes.domain.concretes.business.Advert;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

@Entity
@Table(name = "t_users")
public class User {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    // @Email
    @Column(unique = true, nullable = false)
    private String email;


    //@Pattern
    @Column(unique = true)
    private String phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password_hash", unique = true, nullable = false)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase character, one uppercase character, and one special character (@#$%^&+=)")
    private String passwordHash;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "reset_password_code")
    private String resetPasswordCode;

    private Boolean built_in;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm", timezone = "Turkey")
    @Column(name = "create_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm", timezone = "Turkey")
    @Column(name = "update_at")
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns=@JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private UserRole userRole;

    @OneToMany(mappedBy = "user")
    private Set<Advert> advert=new HashSet<>();

    @PrePersist

    private void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

