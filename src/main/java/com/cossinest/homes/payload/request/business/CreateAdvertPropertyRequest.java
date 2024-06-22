package com.cossinest.homes.payload.request.business;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)

public class CreateAdvertPropertyRequest {

    @NotNull(message = "key can not be blank")
    private Long keyId;

    @NotNull(message = "value can not be null")
    private String value;
}
