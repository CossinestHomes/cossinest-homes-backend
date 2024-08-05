package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.CategoryPropertyValue;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.repository.business.CategoryPropertyValueRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryPropertyValueService {




    private final CategoryPropertyValueRepository categoryPropertyValueRepository;

    //advert için method
    public CategoryPropertyValue getCategoryPropertyValueForAdvert(Object obje){
        return categoryPropertyValueRepository.findValueByName(obje).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.PROPERTY_VALUE_NOT_FOUND));
    }

    public String getPropertyKeyNameByPropertyValue(Long id){
     CategoryPropertyValue categoryPropertyValue=categoryPropertyValueRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.PROPERTY_VALUE_NOT_FOUND));
     return categoryPropertyValue.getCategoryPropertyKeys().getPropertyName();

    }

    public CategoryPropertyValue categoryFindByValue(String value) {

      return categoryPropertyValueRepository.findByValue(value).orElseThrow(()-> new BadRequestException(String.format(ErrorMessages.CATEGORY_VALUE_IS_NOT_FOUND,value)));

    }

    public void resetCategoryPropertyValueTables() {
        categoryPropertyValueRepository.deleteAll();
    }

    @Transactional
    public CategoryPropertyValue saveCategoryPropertyValue(CategoryPropertyValue categoryPropertyValue) {
        return categoryPropertyValueRepository.save(categoryPropertyValue);
    }
}
