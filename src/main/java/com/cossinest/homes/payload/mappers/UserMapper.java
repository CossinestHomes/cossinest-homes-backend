package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.business.Favorites;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.payload.request.user.AuthenticatedUsersRequest;
import com.cossinest.homes.payload.request.user.UserRequest;
import com.cossinest.homes.payload.request.user.UserSaveRequest;
import com.cossinest.homes.payload.request.user.UsersUpdateRequest;
import com.cossinest.homes.payload.response.user.*;
import com.cossinest.homes.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class UserMapper {

    private final MethodHelper methodHelper;
    private final TourRequestMapper tourRequestMapper;
    private final AdvertMapper advertMapper;



    public CustomerResponse customerToCustomerResponse(User user) {
        return CustomerResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .advert(user.getAdvert().stream().map(advertMapper::mapAdvertToAdvertResponse).collect(Collectors.toSet()))
                .favoritesList(user.getFavoritesList().stream().map(Favorites::getId).collect(Collectors.toSet()))
                .tourRequestsResponse(user.getTourRequests().stream().map(tourRequestMapper::tourRequestToTourRequestResponse).collect(Collectors.toSet()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userRole(user.getUserRole().stream().map(UserRole::getRoleName).collect(Collectors.toSet()))
                .tourRequestsResponse(user.getTourRequests().stream().map(tourRequestMapper::tourRequestToTourRequestResponse).collect(Collectors.toSet()))
                .build();
    }

    //TODO LOG  EKLENECEK
    public UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .advert(user.getAdvert() != null ? user.getAdvert().stream().map(advertMapper::mapAdvertToAdvertResponse).collect(Collectors.toSet()) : new HashSet<>())
                .favoritesList(user.getFavoritesList() != null ? user.getFavoritesList().stream().map(Favorites::getId).collect(Collectors.toSet()) : new HashSet<>())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userRole(user.getUserRole() != null ? user.getUserRole().stream().map(UserRole::getRoleName).collect(Collectors.toSet()) : new HashSet<>())
                .tourRequestsResponse(user.getTourRequests() != null ? user.getTourRequests().stream().map(tourRequestMapper::tourRequestToTourRequestResponse).collect(Collectors.toSet()) : new HashSet<>())
                .built_in(user.getBuiltIn())
                .build();
    }

    public AuthenticatedUsersResponse authenticatedUsersResponse(User user) {
       return AuthenticatedUsersResponse.builder()
               .id(user.getId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
               .built_in(user.getBuiltIn())
               .userRole(user.getUserRole().stream().map(UserRole::getRoleName).collect(Collectors.toSet()))
               .tourRequestsResponse(user.getTourRequests().stream().map(tourRequestMapper::tourRequestToTourRequestResponse).collect(Collectors.toSet()))
               .advert(user.getAdvert().stream().map(advertMapper::mapAdvertToAdvertResponse).collect(Collectors.toSet()))
               .favoritesList(user.getFavoritesList().stream().map(Favorites::getId).collect(Collectors.toList()))
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
              //  .tourRequest(user.getTourRequests().stream().map(tourRequestMapper::tourRequestToTourRequestResponse).collect(Collectors.toSet()))
              //  .favorities(user.getFavoritesList().stream().map(Favorites::getId).collect(Collectors.toSet()))
              //  .advert(user.getAdvert().stream().map(advertMapper::mapAdvertToAdvertResponse).collect(Collectors.toSet()))
                .build();
    }

    public User usersUpdateRequestToUser(User user, UsersUpdateRequest request) {
        return user.toBuilder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .build();
    }


    public User userRequestToUser(UserSaveRequest request) {

       return User.builder()
               .email(request.getEmail())
               .firstName(request.getFirstName())
               .lastName(request.getLastName())
               .phone(request.getPhone())
               .builtIn(request.getBuiltIn())
               .build();

    }

    public SignInResponse userToSignInResponse(User newUser){
        return SignInResponse.builder()
                .id(newUser.getId())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .phone(newUser.getPhone())
                .email(newUser.getEmail())
                .build();
    }
}

