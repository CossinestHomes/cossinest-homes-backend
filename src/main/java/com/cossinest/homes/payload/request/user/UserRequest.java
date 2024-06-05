package com.cossinest.homes.payload.request.user;

import com.cossinest.homes.payload.request.abstracts.BaseUserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest extends BaseUserRequest {
}
