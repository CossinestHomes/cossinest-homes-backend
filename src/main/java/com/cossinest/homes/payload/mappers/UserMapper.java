package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.payload.response.user.UserResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class UserMapper {



//    public UserResponse userToUserResponseWithoutPassword(User user) {
//        return UserResponse.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .phone(user.getPhone())
//                .createAt(LocalDateTime.now())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .built_in(user.getBuilt_in())
//                .build();
//    }
}
