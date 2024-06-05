package com.cossinest.homes.contactMessage.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessageRequest {

    @NotNull(message = "Please enter your name")
    @Size(min = 2, max = 30 , message = "Your name must be a maximum of 30 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your name must be consist of character")
    private String first_name;

    @NotNull(message = "Please enter your lastname")
    @Size(min = 2, max = 30 , message = "Your lastname must be a maximum of 30 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your lastname must be consist of character")
    private String last_name;

    @NotNull(message = "Please enter your email")
    @Email(message = "Please enter valid email address")
    @Size(min =5 , max = 60, message = "Your email address must be a maximum of 60 characters.")
    private String email;

    @NotNull(message = "Please enter your message")
    @Size(min = 3, max = 100, message = "Your message should be at least 3 characters")
    private String message;
}
