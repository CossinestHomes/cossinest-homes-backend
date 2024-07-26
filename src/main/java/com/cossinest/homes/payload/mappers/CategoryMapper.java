package com.cossinest.homes.payload.mappers;


import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.payload.response.business.CategoryResponseDTO;
import com.cossinest.homes.payload.response.business.PropertyKeyResponse;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
@RequiredArgsConstructor
public class CategoryMapper {



    // CategoryENTITY ====> DTO:

    public CategoryResponseDTO mapCategoryToCategoryResponseDTO(Category category){

        return CategoryResponseDTO.builder()
                .catId(category.getId())
                .title(category.getTitle())
                .icon(category.getIcon())
                .builtIn(category.getBuiltIn())
                .seq(category.getSeq())
                .slug(category.getSlug())
                .active(category.getActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .categoryPropertyKeys(category.getCategoryPropertyKeys())
                .build();
    }


    // DTO ====> CategoryENTITY (POJO) :

    public Category mapCategoryRequestDTOToCategory(CategoryRequestDTO categoryRequestDTO){

        return Category.builder()
                .title(categoryRequestDTO.getTitle())
                .icon(categoryRequestDTO.getIcon())
                .seq(categoryRequestDTO.getSeq())
                .slug(categoryRequestDTO.getSlug())
                .active(categoryRequestDTO.isActive())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .builtIn(false)
                .build();
    }

    //Property key pojo ---> Property key response

    public PropertyKeyResponse mapPropertyKeytoPropertyKeyResponse(CategoryPropertyKey categoryPropertyKey) {
        return PropertyKeyResponse.builder()
                .id(categoryPropertyKey.getId())
                .propertyName(categoryPropertyKey.getPropertyName())
                .builtIn(false)
                .build();
    }


}
