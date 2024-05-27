package com.cossinest.homes.payload.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdatePasswordRequest {



    @NotNull
    private String passwordHash;

    @NotNull
    private String resetPasswordCode;



}
