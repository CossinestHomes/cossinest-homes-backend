package com.cossinest.homes.payload.request.abstracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@SuperBuilder
public class BaseUserRequest extends AbstractUserRequest{


    @NotNull(message = "Please enter your password")
    private String passwordHash;

    @NotNull(message = "Please enter your password")
    private String resetPasswordCode;




}
