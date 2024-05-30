package com.cossinest.homes.payload.response.user;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.domain.concretes.user.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthenticatedUsersResponse {


    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Boolean built_in = false;


    private Set<UserRole> userRole;


    private Set<Advert> advert;


    private Set<TourRequest>tourRequests;

    // todo:favori ve log eklenecek

}
