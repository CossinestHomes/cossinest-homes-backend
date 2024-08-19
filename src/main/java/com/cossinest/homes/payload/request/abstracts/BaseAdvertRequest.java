package com.cossinest.homes.payload.request.abstracts;

import com.cossinest.homes.domain.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseAdvertRequest extends AbstractAdvertRequest  {
    @NotNull(message = "Status must not be empty")
    private int status;


    private Boolean builtIn;

    private Boolean isActive= true;


    private Integer viewCount;
}
