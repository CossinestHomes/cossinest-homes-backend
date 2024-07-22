package com.cossinest.homes.payload.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SignInResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

}
