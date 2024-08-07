package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.cossinest.homes.domain.enums.CategoryPropertyKeyType;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.CategoryMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.PropertyKeyRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.PropertyKeyResponse;
import com.cossinest.homes.repository.business.CategoryPropertyKeyRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryPropertyKeyService {

    private final CategoryMapper categoryMapper;
    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private final CategoryService categoryService;


    public CategoryPropertyKey findPropertyKeyById(Long id) {

        return categoryPropertyKeyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id :" + id));
    }


    public ResponseMessage<PropertyKeyResponse> updatePropertyKey(Long id, PropertyKeyRequest propertyKeyRequest) {

        boolean existName = categoryPropertyKeyRepository.existsByPropertyName(propertyKeyRequest.getPropertyName());

        CategoryPropertyKey categoryPropertyKey = findPropertyKeyById(id);

        if (categoryPropertyKey.getBuiltIn()) {
            throw new ResourceNotFoundException("This categoryPropertyKey cannot be updated");
        }

        if (existName && !propertyKeyRequest.getPropertyName().equals(categoryPropertyKey.getPropertyName())) {

            throw new ConflictException(ErrorMessages.PROPERTY_KEY_NAME_ALREADY_EXIST);
        }

        categoryPropertyKey.setPropertyName(propertyKeyRequest.getPropertyName());
        CategoryPropertyKey updatedPropertyKey = categoryPropertyKeyRepository.save(categoryPropertyKey);


        return ResponseMessage.<PropertyKeyResponse>builder()
                .object(categoryMapper.mapPropertyKeytoPropertyKeyResponse(updatedPropertyKey))
                .message(SuccesMessages.CATEGORY_PROPERTY_KEY_UPDATED_SUCCESS)
                .status(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<PropertyKeyResponse> deletePropertyKey(Long id) {

        CategoryPropertyKey categoryPropertyKey = findPropertyKeyById(id);

        if (categoryPropertyKey.getBuiltIn()) {
            throw new ResourceNotFoundException("This categoryPropertyKey cannot be deleted");
        }

        categoryPropertyKeyRepository.delete(categoryPropertyKey);

        return ResponseMessage.<PropertyKeyResponse>builder()
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
        Category category = categoryService.findCategoryById(id);
        Set<CategoryPropertyKey> properKeysOfCategory = categoryPropertyKeyRepository.findByCategory_Id(category.getId());

        return properKeysOfCategory.stream()
                .map(categoryMapper::mapPropertyKeytoPropertyKeyResponse)
                .collect(Collectors.toSet());
    }

    public void generateCategoryPropertyKeys() {
        if (categoryPropertyKeyRepository.findAll().isEmpty()) {

            Category ev = categoryService.getCategoryById(1L);

            CategoryPropertyKey evProps1 = CategoryPropertyKey.builder()
                    .propertyName("Oda Salon Sayısı")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(ev)
                    .build();
            CategoryPropertyKey evProps2 = CategoryPropertyKey.builder()
                    .propertyName("Banyo Sayısı")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(ev)
                    .build();
            CategoryPropertyKey evProps3 = CategoryPropertyKey.builder()
                    .propertyName("Bina Yaşı")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(ev)
                    .build();
            CategoryPropertyKey evProps4 = CategoryPropertyKey.builder()
                    .propertyName("Brüt Metrekare")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(ev)
                    .build();
            CategoryPropertyKey evProps5 = CategoryPropertyKey.builder()
                    .propertyName("Bahçe")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.BOOLEAN)
                    .category(ev)
                    .build();
            CategoryPropertyKey evProps6 = CategoryPropertyKey.builder()
                    .propertyName("Garaj")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.BOOLEAN)
                    .category(ev)
                    .build();
            CategoryPropertyKey evProps7 = CategoryPropertyKey.builder()
                    .propertyName("Min. Fiyat")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.DOUBLE)
                    .category(ev)
                    .build();
            CategoryPropertyKey evProps8 = CategoryPropertyKey.builder()
                    .propertyName("Max.Fiyat")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.DOUBLE)
                    .category(ev)
                    .build();

            Category apartman = categoryService.getCategoryById(2L);

            CategoryPropertyKey apartmanProps1 = CategoryPropertyKey.builder()
                    .propertyName("Oda Salon Sayısı")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(apartman)
                    .build();
            CategoryPropertyKey apartmanProps2 = CategoryPropertyKey.builder()
                    .propertyName("Banyo Sayısı")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(apartman)
                    .build();
            CategoryPropertyKey apartmanProps3 = CategoryPropertyKey.builder()
                    .propertyName("Bina Yaşı")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(apartman)
                    .build();
            CategoryPropertyKey apartmanProps4 = CategoryPropertyKey.builder()
                    .propertyName("Brüt Metrekare")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(apartman)
                    .build();
            CategoryPropertyKey apartmanProps5 = CategoryPropertyKey.builder()
                    .propertyName("Balkon")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.BOOLEAN)
                    .category(apartman)
                    .build();
            CategoryPropertyKey apartmanProps6 = CategoryPropertyKey.builder()
                    .propertyName("Garaj")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.BOOLEAN)
                    .category(apartman)
                    .build();
            CategoryPropertyKey apartmanProps7 = CategoryPropertyKey.builder()
                    .propertyName("Min. Fiyat")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.DOUBLE)
                    .category(apartman)
                    .build();
            CategoryPropertyKey apartmanProps8 = CategoryPropertyKey.builder()
                    .propertyName("Max.Fiyat")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.DOUBLE)
                    .category(apartman)
                    .build();

            Category ofis = categoryService.getCategoryById(3L);

            CategoryPropertyKey ofisProps1 = CategoryPropertyKey.builder()
                    .propertyName("Oda Salon Sayısı")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(ofis)
                    .build();
            CategoryPropertyKey ofisProps2 = CategoryPropertyKey.builder()
                    .propertyName("Banyo Sayısı")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(ofis)
                    .build();
            CategoryPropertyKey ofisProps3 = CategoryPropertyKey.builder()
                    .propertyName("Bina Yaşı")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(ofis)
                    .build();
            CategoryPropertyKey ofisProps4 = CategoryPropertyKey.builder()
                    .propertyName("Brüt Metrekare")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(ofis)
                    .build();
            CategoryPropertyKey ofisProps5 = CategoryPropertyKey.builder()
                    .propertyName("Depo")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.BOOLEAN)
                    .category(ofis)
                    .build();
            CategoryPropertyKey ofisProps6 = CategoryPropertyKey.builder()
                    .propertyName("Garaj")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.BOOLEAN)
                    .category(ofis)
                    .build();
            CategoryPropertyKey ofisProps7 = CategoryPropertyKey.builder()
                    .propertyName("Min. Fiyat")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.DOUBLE)
                    .category(ofis)
                    .build();
            CategoryPropertyKey ofisProps8 = CategoryPropertyKey.builder()
                    .propertyName("Max.Fiyat")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.DOUBLE)
                    .category(ofis)
                    .build();

            Category villa = categoryService.getCategoryById(4L);

            CategoryPropertyKey villaProps1 = CategoryPropertyKey.builder()
                    .propertyName("Oda Salon Sayısı")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(villa)
                    .build();
            CategoryPropertyKey villaProps2 = CategoryPropertyKey.builder()
                    .propertyName("Banyo Sayısı")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(villa)
                    .build();
            CategoryPropertyKey villaProps3 = CategoryPropertyKey.builder()
                    .propertyName("Bina Yaşı")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(villa)
                    .build();
            CategoryPropertyKey villaProps4 = CategoryPropertyKey.builder()
                    .propertyName("Brüt Metrekare")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(villa)
                    .build();
            CategoryPropertyKey villaProps5 = CategoryPropertyKey.builder()
                    .propertyName("Depo")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.BOOLEAN)
                    .category(villa)
                    .build();
            CategoryPropertyKey villaProps6 = CategoryPropertyKey.builder()
                    .propertyName("Garaj")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.BOOLEAN)
                    .category(villa)
                    .build();
            CategoryPropertyKey villaProps7 = CategoryPropertyKey.builder()
                    .propertyName("Min. Fiyat")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.DOUBLE)
                    .category(villa)
                    .build();
            CategoryPropertyKey villaProps8 = CategoryPropertyKey.builder()
                    .propertyName("Max.Fiyat")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.DOUBLE)
                    .category(villa)
                    .build();

            Category arsa = categoryService.getCategoryById(5L);

            CategoryPropertyKey arsaProps1 = CategoryPropertyKey.builder()
                    .propertyName("Metrekare")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.NUMBER)
                    .category(arsa)
                    .build();
            CategoryPropertyKey arsaProps2 = CategoryPropertyKey.builder()
                    .propertyName("Min. Fiyat")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.DOUBLE)
                    .category(arsa)
                    .build();
            CategoryPropertyKey arsaProps3 = CategoryPropertyKey.builder()
                    .propertyName("Max.Fiyat")
                    .builtIn(true)
                    .keyType(CategoryPropertyKeyType.DOUBLE)
                    .category(arsa)
                    .build();


            categoryPropertyKeyRepository.saveAll(List.of(evProps1, evProps2,evProps3,evProps4,evProps5,evProps6,evProps7,evProps8,
                    apartmanProps1, apartmanProps2, apartmanProps3,apartmanProps4,apartmanProps5,apartmanProps6,apartmanProps7,apartmanProps8,
                    ofisProps1,ofisProps2,ofisProps3,ofisProps4,ofisProps5,ofisProps6,ofisProps7,ofisProps8,
                    villaProps1,villaProps2,villaProps3,villaProps4,villaProps5,villaProps6,villaProps7,villaProps8,
                    arsaProps1,arsaProps2,arsaProps3
                    ));
        }

    }
}










