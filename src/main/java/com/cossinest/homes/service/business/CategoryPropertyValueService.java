package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.CategoryPropertyValue;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.repository.business.CategoryPropertyValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryPropertyValueService {

    private final CategoryPropertyValueRepository categoryPropertyValueRepository;

    //advert için method
    public CategoryPropertyValue getCategoryPropertyValueForAdvert(Object obje){
        return categoryPropertyValueRepository.findValueByName(obje).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.PROPERTY_VALUE_NOT_FOUND));
    }
}
