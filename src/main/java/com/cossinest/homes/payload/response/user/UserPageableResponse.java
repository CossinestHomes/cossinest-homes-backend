package com.cossinest.homes.payload.response.user;

import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.RoleType;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPageableResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private Set<String> userRole;


}
