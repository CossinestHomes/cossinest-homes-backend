package com.cossinest.homes.payload.mappers;


import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;

import com.cossinest.homes.payload.response.business.CategoryPropKeyResponseDTO;
import com.cossinest.homes.payload.response.business.CategoryPropKeyssResponseDTO;
import com.cossinest.homes.payload.response.business.CategoryResponseDTO;
import com.cossinest.homes.service.helper.MethodHelper;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;


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


    // DTO ====> CategoryENTITY :

    public Category mapCategoryRequestDTOToCategory(CategoryRequestDTO categoryRequestDTO){

        return Category.builder()
                .title(categoryRequestDTO.getTitle())
                .icon(categoryRequestDTO.getIcon())
                .seq(categoryRequestDTO.getSeq())
                .slug(categoryRequestDTO.getSlug())
                .active(categoryRequestDTO.isActive())
                .build();
    }


    // CategoryPropertyKeyENTITY ====> DTO:

    public CategoryPropKeyResponseDTO mapCategPropKeyToCategPropKeyResponseDTO(CategoryPropertyKey categoryPropertyKey){

        return CategoryPropKeyResponseDTO.builder()
                .id(categoryPropertyKey.getId())
                .name(categoryPropertyKey.getName())
                .builtIn(categoryPropertyKey.getBuiltIn())
                .build();
    }


   // CategoryPropertyKeyssENTITY ====> DTO:

    public CategoryPropKeyssResponseDTO mapCategPropKeyssToCategPropKeyssResponseDTO(List<CategoryPropertyKey> categoryProperKeys){

        CategoryPropKeyssResponseDTO dto = new CategoryPropKeyssResponseDTO();
        dto.setCategoryPropertyKeys(categoryProperKeys);

        return dto;
    }

}
