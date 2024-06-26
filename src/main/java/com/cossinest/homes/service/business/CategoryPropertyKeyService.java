package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.CategoryMapper;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.payload.response.business.CategoryPropKeyResponseDTO;
import com.cossinest.homes.repository.business.CategoryPropertyKeyRepository;
import com.cossinest.homes.repository.business.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryPropertyKeyService {



    private final CategoryMapper categoryMapper;

    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;


    public CategoryPropertyKey findPropertyKeyById(Long id) {

        return categoryPropertyKeyRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with id :" + id));
    }


    public CategoryPropKeyResponseDTO updatePropertyKey(Long id, CategoryRequestDTO categoryRequestDTO) {

        boolean existName = categoryPropertyKeyRepository.existsByName(categoryRequestDTO.getName());

        CategoryPropertyKey categoryPropertyKey = findPropertyKeyById(id);

        if(categoryPropertyKey.getBuiltIn()){
            throw new ResourceNotFoundException("This categoryPropertyKey cannot be updated");
        }

        if( existName && ! categoryRequestDTO.getName().equals(categoryPropertyKey.getName()) ) {

            throw new ConflictException("Name is already exist ");
        }

        categoryPropertyKey.setName(categoryRequestDTO.getName());
        CategoryPropertyKey updatedPropertyKey = categoryPropertyKeyRepository.save(categoryPropertyKey);


        return categoryMapper.mapCategPropKeyToCategPropKeyResponseDTO(updatedPropertyKey);
    }

    public  CategoryPropKeyResponseDTO deletePropertyKey(Long id) {

        CategoryPropertyKey categoryPropertyKey = findPropertyKeyById(id);

        if(categoryPropertyKey.getBuiltIn()){
            throw new ResourceNotFoundException("This categoryPropertyKey cannot be deleted");
        }

        categoryPropertyKeyRepository.delete(categoryPropertyKey);

        return categoryMapper.mapCategPropKeyToCategPropKeyResponseDTO(categoryPropertyKey);
    }


    @Transactional
    public void resetCategoryPropertyKeyTables() {
     //   categoryPropertyKeyRepository.deleteByBuiltIn(false);
    }
}
