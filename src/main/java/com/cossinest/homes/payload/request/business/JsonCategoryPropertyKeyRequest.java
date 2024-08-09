package com.cossinest.homes.payload.request.business;

import com.cossinest.homes.domain.enums.CategoryPropertyKeyType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class JsonCategoryPropertyKeyRequest {

    private Long id;
    private String[] propertyName;
    private Boolean builtIn;


}
