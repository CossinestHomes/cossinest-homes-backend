package com.cossinest.homes.payload.request.abstracts;

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

    @NotNull(message = "Built in must not be empty")
    private Boolean builtIn;

    @NotNull(message = "Is active must not be empty")
    private Boolean isActive;

    @NotNull(message = "View count must not be empty")
    private Integer viewCount;
}
