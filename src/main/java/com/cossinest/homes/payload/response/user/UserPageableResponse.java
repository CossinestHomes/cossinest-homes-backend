package com.cossinest.homes.payload.response.user;

import lombok.*;

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


}
