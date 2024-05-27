package com.cossinest.homes.domain.concretes.user;

import com.cossinest.homes.domain.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10) //DB de varchar olarak saklanacak. Bunu yapmazsak default da 255 olur
    private RoleType roleType;

    private String roleName;
}