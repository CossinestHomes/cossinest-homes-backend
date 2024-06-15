package com.cossinest.homes.payload.response.business;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class CategoryPropKeyResponseDTO{



    private Long id;

    private String name;

    private Boolean builtIn = false;


}
