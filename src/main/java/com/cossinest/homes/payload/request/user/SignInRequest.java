package com.cossinest.homes.payload.request.user;

import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SignInRequest {

    @NotBlank
    @Size(min = 2, max = 30, message = "First name '${validatedValue}' must be between {min} and {max}")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Your first name must be consist of the characters a-z")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 30, message = "Last name '${validatedValue}' must be between {min} and {max} long")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Your last name must be consist of the characters a-z")
    private String lastName;

    @Size(min = 10, max = 12, message = "Your phone number should be at least 12 chars")
    @Pattern(regexp = "^(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$",
            message = "Please enter valid phone number")
    @NotBlank(message = "Please enter your phone number")
    private String phone;

    @NotBlank(message = "Please enter your email")
    @Email(message = "Please enter valid email")
    @Size(min = 10, max = 80, message = "Your email '${validatedValue}' should be between {min} and {max} chars")
    private String email;

    @NotBlank(message = "Enter a valid Password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase character, one uppercase character, and one special character (@#$%^&+=)")
    private String password;

    private Set<RoleType> role;

}
