package com.cossinest.homes.payload.request.user;

import com.cossinest.homes.payload.request.abstracts.AbstractUserRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UserRequestWithoutPassword extends AbstractUserRequest {

    @NotNull(message = "Please select tour Reqeust")
    private Set<Long> tourRequestIdList;

    @NotNull(message = "Please select adverts")
    private Set<Long> advertIdList;

    //TODO LOG VE FAVORITES


}
