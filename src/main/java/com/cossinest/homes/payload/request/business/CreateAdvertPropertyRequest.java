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

    @NotBlank(message = "key can not be blank")
    private String key;

    @NotNull(message = "value can not be null")
    private Object value;
}
