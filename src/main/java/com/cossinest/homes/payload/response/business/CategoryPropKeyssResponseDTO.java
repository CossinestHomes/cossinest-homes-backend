package com.cossinest.homes.payload.response.business;

import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class CategoryPropKeyssResponseDTO {



    private Long id;
    private Set<CategoryPropertyKey> categoryPropertyKeys;

}
