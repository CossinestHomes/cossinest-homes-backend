package com.cossinest.homes.payload.mappers;


import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.payload.response.business.CategoryResponseDTO;
import com.cossinest.homes.service.helper.MethodHelper;
import lombok.*;
import org.springframework.stereotype.Component;



@Data
@Component
public class CategoryMapper {




    private MethodHelper methodHelper;



    // CategoryENTITY ====> DTO:

    public CategoryResponseDTO mapCategoryToCategoryResponceDTO(Category category){

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













}
