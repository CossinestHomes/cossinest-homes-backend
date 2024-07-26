package com.cossinest.homes.payload.request.abstracts;


import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class AbstractUserRequest {

    @NotBlank
    @Size(min = 2, max = 30, message = "First name '${validatedValue}' must be between {min} and {max}")
    @Pattern(regexp = "^[a-zA-ZçÇğĞıİöÖşŞüÜ]+$", message = "Your first name must be consist of the characters a-z")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 30, message = "Last name '${validatedValue}' must be between {min} and {max} long")
    @Pattern(regexp = "^[a-zA-ZçÇğĞıİöÖşŞüÜ]+$", message = "Your last name must be consist of the characters a-z")
    private String lastName;


    @NotBlank(message = "Please enter your email")
    @Email(message = "Please enter valid email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    @Size(min = 10, max = 80, message = "Your email '${validatedValue}' should be between {min} and {max} chars")
    private String email;


      @Size(min = 12, max = 12, message = "Your phone number should be at least 12 chars")
       @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
              message = "Please enter valid phone number")
    @NotBlank(message = "Please enter your phone number")
    private String phone;








}
