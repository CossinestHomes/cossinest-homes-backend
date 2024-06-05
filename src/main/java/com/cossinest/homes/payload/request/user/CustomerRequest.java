package com.cossinest.homes.payload.request.user;


import jakarta.validation.constraints.*;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerRequest {

    @NotBlank(message = "enter valid email adress")
    @Email(message = "enter valid email adress")
    private String email;

    @NotBlank(message = "Enter a valid Password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase character, one uppercase character, and one special character (@#$%^&+=)")
    private String password;


}
