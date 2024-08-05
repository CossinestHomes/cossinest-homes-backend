package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.CategoryMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.PropertyKeyRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.PropertyKeyResponse;
import com.cossinest.homes.repository.business.CategoryPropertyKeyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryPropertyKeyService {

    private final CategoryMapper categoryMapper;
    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private final CategoryService categoryService;


    public CategoryPropertyKey findPropertyKeyById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null??");
        }

        return categoryPropertyKeyRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with id :" + id));
    }


    public ResponseMessage<PropertyKeyResponse> updatePropertyKey(Long id, PropertyKeyRequest propertyKeyRequest) {

        boolean existName = categoryPropertyKeyRepository.existsByPropertyName(propertyKeyRequest.getPropertyName());

        CategoryPropertyKey categoryPropertyKey = findPropertyKeyById(id);

        if(categoryPropertyKey.getBuiltIn()){
            throw new ResourceNotFoundException("This categoryPropertyKey cannot be updated");
        }

        if( existName && ! propertyKeyRequest.getPropertyName().equals(categoryPropertyKey.getPropertyName()) ) {

            throw new ConflictException(ErrorMessages.PROPERTY_KEY_NAME_ALREADY_EXIST);
        }

        categoryPropertyKey.setPropertyName(propertyKeyRequest.getPropertyName());
        CategoryPropertyKey updatedPropertyKey = categoryPropertyKeyRepository.save(categoryPropertyKey);


        return ResponseMessage.<PropertyKeyResponse> builder()
                .object(categoryMapper.mapPropertyKeytoPropertyKeyResponse(updatedPropertyKey))
                .message(SuccesMessages.CATEGORY_PROPERTY_KEY_UPDATED_SUCCESS)
                .status(HttpStatus.OK)
                .build();
    }

    public  ResponseMessage<PropertyKeyResponse> deletePropertyKey(Long id) {

        CategoryPropertyKey categoryPropertyKey = findPropertyKeyById(id);

        if(categoryPropertyKey.getBuiltIn()){
            throw new ResourceNotFoundException("This categoryPropertyKey cannot be deleted");
        }

        categoryPropertyKeyRepository.delete(categoryPropertyKey);

        return ResponseMessage.<PropertyKeyResponse> builder()
                .object(categoryMapper.mapPropertyKeytoPropertyKeyResponse(categoryPropertyKey))
                .message(SuccesMessages.CATEGORY_PROPERTY_KEY_DELETED_SUCCESS)
                .status(HttpStatus.OK)
                .build();
    }


    @Transactional
    public void resetCategoryPropertyKeyTables() {
     //   categoryPropertyKeyRepository.deleteByBuiltIn(false);
    }


    public Set<PropertyKeyResponse> findByCategoryIdEquals(Long id) {
        Category category= categoryService.findCategoryById(id);
        Set<CategoryPropertyKey> properKeysOfCategory= categoryPropertyKeyRepository.findByCategory_IdEquals(category.getId());

        return  properKeysOfCategory.stream()
                .map(categoryMapper::mapPropertyKeytoPropertyKeyResponse)
                .collect(Collectors.toSet());

    }


}
