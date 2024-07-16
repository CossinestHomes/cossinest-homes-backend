package com.cossinest.homes.payload.request.user;

import com.cossinest.homes.domain.concretes.business.Favorites;
import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.payload.request.abstracts.BaseUserRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserSaveRequest extends BaseUserRequest {


    private Set<String> roles;


    private Set<Long> tourRequestIdList;


    private Set<Long> advertIdList;


    private Set<Long>tourRequests;

    @NotNull(message = "Please select builtIn value")
    private Boolean builtIn;


    private List<Long> favoritesList;

}
