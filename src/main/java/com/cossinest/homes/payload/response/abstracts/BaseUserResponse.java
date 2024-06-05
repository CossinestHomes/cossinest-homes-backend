package com.cossinest.homes.payload.response.abstracts;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseUserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Set<String> userRole;

    private Set<Advert> advert;

    private Set<TourRequestResponse> tourRequestsResponse;


    //TODO: Favorities ve Logs


}
