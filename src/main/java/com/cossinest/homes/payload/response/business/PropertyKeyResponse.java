package com.cossinest.homes.payload.response.business;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class PropertyKeyResponse {

    private Long id;
    private String propertyName;
    private Boolean builtIn = false;
}
