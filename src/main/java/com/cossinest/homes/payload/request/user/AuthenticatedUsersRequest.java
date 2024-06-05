package com.cossinest.homes.payload.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthenticatedUsersRequest {

    @NotNull(message = "First name can not be a null")
    @Size(min = 2, max = 30, message = "First name '${validatedValue}' must be between {min} and {max}")
    private String firstName;
    @NotNull(message = "Last name can not be a null")
    @Size(min = 2, max = 30, message = "Last name '${validatedValue}' must be between {min} and {max}")
    private String lastName;
    @Email
    @NotNull(message = "Email can not be a null")
    private String email;
    @NotNull(message = "Phone number can not be a null")
    private String phone;




}
