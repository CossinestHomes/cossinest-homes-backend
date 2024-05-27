package com.cossinest.homes.payload.request.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@SuperBuilder
public class AbstractUserRequest {

    @NotNull
    @Size(min = 2,max=30)
    @Pattern(regexp="\\A(?!\\s*\\Z).+", message = "Your first name must be consist of the characters a-z")
    private String firstName;

    @NotNull
    @Size(min = 2,max = 30)
    @Pattern(regexp="\\A(?!\\s*\\Z).+", message = "Your last name must be consist of the characters a-z")
    private String lastName;


    @NotNull(message = "Please enter your email")
    @Email(message = "Please enter valid email")
    @Size(min = 10, max = 80, message = "Your email should be between 10 and 80 chars")
    private String email;


  //  @Size(min = 12, max = 12, message = "Your phone number should be at least 12 chars")
 //   @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
  //          message = "Please enter valid phone number")
    @NotNull(message = "Please enter your birth phone number")
    private String phone;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name="create_at",nullable = false)
 //   @Past(message = "Create time can not be in the future")
    private LocalDateTime createAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    private Boolean built_in=false;

}
