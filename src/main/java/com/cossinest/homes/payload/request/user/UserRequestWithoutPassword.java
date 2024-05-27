package com.cossinest.homes.payload.request.user;

import com.cossinest.homes.payload.request.abstracts.AbstractUserRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequestWithoutPassword extends AbstractUserRequest {
}
