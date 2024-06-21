package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.repository.business.CategoryPropertyKeyRepository;
import com.cossinest.homes.repository.business.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryPropertyKeyService {




    @Autowired
    private CategoryPropertyKeyRepository categoryPropertyKeyRepository;

    public CategoryPropertyKey findPropertyKey(Long id) {

        return categoryPropertyKeyRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with id :" + id));
    }


    public CategoryPropertyKey updatePropertyKey(Long id, CategoryRequestDTO categoryRequestDTO) {

        boolean existName = categoryPropertyKeyRepository.existsByName(categoryRequestDTO.getName());

        CategoryPropertyKey categoryPropertyKey = findPropertyKey(id);

        if( existName && ! categoryRequestDTO.getName().equals(categoryPropertyKey.getName()) ) {

            throw new ConflictException("Email is already exist ");
        }

        categoryPropertyKey.setName(categoryRequestDTO.getName());
        categoryPropertyKeyRepository.save(categoryPropertyKey);


        return categoryPropertyKey;

    }

    public CategoryPropertyKey deletePropertyKey(Long id) {

        CategoryPropertyKey categoryPropertyKey = findPropertyKey(id);
        categoryPropertyKeyRepository.delete(categoryPropertyKey);

        return categoryPropertyKey;
    }

    @Transactional
    public void resetCategoryPropertyKeyTables() {
        categoryPropertyKeyRepository.deleteByBuiltIn(false);
    }
}
