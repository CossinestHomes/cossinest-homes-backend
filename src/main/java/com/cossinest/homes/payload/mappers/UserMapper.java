package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.payload.request.user.AuthenticatedUsersRequest;
import com.cossinest.homes.payload.request.user.UsersUpdateRequest;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import com.cossinest.homes.payload.response.user.CustomerResponse;
import com.cossinest.homes.payload.response.user.UserPageableResponse;
import com.cossinest.homes.payload.response.user.UserResponse;
import com.cossinest.homes.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class UserMapper {

    private final MethodHelper methodHelper;

    public CustomerResponse customerToCustomerResponse(User user) {
        return CustomerResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .advert(user.getAdvert())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userRole(user.getUserRole().stream().map(UserRole::getRoleName).collect(Collectors.toSet()))
                .tourRequests(user.getTourRequests())
                .build();
    }

    //TODO LOG VE FAVORI EKLENECEK
    public UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .advert(user.getAdvert())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userRole(user.getUserRole().stream().map(UserRole::getRoleName).collect(Collectors.toSet()))
                .tourRequests(user.getTourRequests())
                .built_in(user.getBuilt_in())
                .build();
    }


    public AuthenticatedUsersResponse authenticatedUsersResponse(User user) {
       return AuthenticatedUsersResponse.builder()
               .id(user.getId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
               .built_in(user.getBuilt_in())
               .userRole(user.getUserRole().stream().map(UserRole::getRoleName).collect(Collectors.toSet()))
               .tourRequests(user.getTourRequests())
               .advert(user.getAdvert())
                .build();
    }

    public User authenticatedUsersRequestToAutheticatedUser(User user, AuthenticatedUsersRequest request) {

       return user.toBuilder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }


    public UserPageableResponse usersToUserPageableResponse(User user) {

        return UserPageableResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userRole(user.getUserRole().stream().map(UserRole::getRoleName).collect(Collectors.toSet()))
                .build();
    }

    public User usersUpdateRequestToUser(User user, UsersUpdateRequest request) {
        return user.toBuilder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .build();
    }




}

