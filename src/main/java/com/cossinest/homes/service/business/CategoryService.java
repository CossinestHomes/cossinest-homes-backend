package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.*;


import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.payload.mappers.CategoryMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;

import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.payload.request.business.PropertyKeyRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.*;

import com.cossinest.homes.repository.business.AdvertRepository;
import com.cossinest.homes.repository.business.CategoryPropertyKeyRepository;
import com.cossinest.homes.repository.business.CategoryRepository;
import com.cossinest.homes.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
@Service                            // @Service anot icinde @Component anot. da var
public class CategoryService {


    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private final AdvertRepository advertRepository;
    private final PageableHelper pageableHelper;


    //advert için yardımcı method
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException(ErrorMessages.CATEGORY_NOT_FOUND));
    }


//    public Page<CategoryResponseDTO> getActiveCategoriesWithPage(String query, int page, int size, String sort, String type) {
//
//        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
//
//        Page<Category> categories = categoryRepository.findByActiveTrue(query, pageable);
//
//        return categories.map(categoryMapper::mapCategoryToCategoryResponseDTO);
//
//    }

    public Page<CategoryResponseDTO> getCategories(String query, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));

        Page<Category> categoryPage = categoryRepository.findByTitleContainingAndIsActiveTrue(query, pageable);
        return  categoryPage.map(categoryMapper::mapCategoryToCategoryResponseDTO);
    }



//    public Page<CategoryResponseDTO> getAllCategoriesWithPage(String query, int page, int size, String sort, String type) {
//
//        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
//
//        Page<Category> categoryList;
//        if (query != null && !query.isEmpty()) {
//            categoryList = categoryRepository.findByTitleContaining(query, pageable);
//        } else {
//            categoryList = categoryRepository.findAll(pageable);
//        }
//
//        return categoryList.map(categoryMapper::mapCategoryToCategoryResponseDTO);
//    }

    public Page<CategoryResponseDTO> getAllCategories(String query, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));
        Page<Category> categoryPage;

        if (query != null && !query.isEmpty()) {
            categoryPage = categoryRepository.findByTitleContaining(query, pageable);
        } else {
            categoryPage = categoryRepository.findAll(pageable);
        }
        return categoryPage.map(categoryMapper::mapCategoryToCategoryResponseDTO);
    }


    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND + id));
    }


    public ResponseMessage<CategoryResponseDTO> findCategoryWithId(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND + id));

        return ResponseMessage.<CategoryResponseDTO>builder()
                .object(categoryMapper.mapCategoryToCategoryResponseDTO(category))
                .message(SuccesMessages.RETURNED_A_CATEGORY)
                .status(HttpStatus.OK)
                .build();
    }


    public ResponseMessage<CategoryResponseDTO> createCategory(CategoryRequestDTO categoryRequestDTO) {

        if (categoryRepository.existsByTitle(categoryRequestDTO.getTitle())) {

            throw new ConflictException("Category " + categoryRequestDTO.getTitle() + " is already exist ");
        }

        Category category = categoryMapper.mapCategoryRequestDTOToCategory(categoryRequestDTO);

        if (category.getTitle().equalsIgnoreCase("Arsa")) {
            category.setBuiltIn(true);
        }

        category.generateSlug();

        Set<CategoryPropertyKey> categoryPropertyKeys = new HashSet<>();
        if (categoryRequestDTO.getCategoryPropertyKeys() != null) {
            for (PropertyKeyRequest propertyKeyRequest : categoryRequestDTO.getCategoryPropertyKeys()) {
                CategoryPropertyKey categoryPropertyKey = new CategoryPropertyKey();
                categoryPropertyKey.setPropertyName(propertyKeyRequest.getPropertyName());
                categoryPropertyKey.setCategory(category);  // Kategori ile ilişkilendir
                categoryPropertyKeys.add(categoryPropertyKey);
            }
        }

        // Kategori'ye CategoryPropertyKey'leri ekle
        category.setCategoryPropertyKeys(categoryPropertyKeys);

        Category createdCategory = categoryRepository.save(category);

        return ResponseMessage.<CategoryResponseDTO>builder()
                .object(categoryMapper.mapCategoryToCategoryResponseDTO(createdCategory))
                .message(SuccesMessages.CATEGORY_CREATED_SUCCESS)
                .status(HttpStatus.CREATED)
                .build();
    }


    public ResponseMessage<CategoryResponseDTO> updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {

        Category category = findCategoryById(id);
      //  boolean existTitle = categoryRepository.existsByTitle(categoryRequestDTO.getTitle());  // categoryRequest ile gelen Title DB'de VAR mi?

        if (category.getBuiltIn()) {
            throw new ResourceNotFoundException(ErrorMessages.CATEGORY_CAN_NOT_UPDATE);
        }

//        if (existTitle) {
//            throw new ConflictException(ErrorMessages.CATEGORY_TITLE_ALREADY_EXIST);

/*      1.senaryo: categoryRequest ile gelen Title Arsa,                 DB'de MEVCUT Title : Arsa            --> TRUE && FALSE    (UPDATE OLUR)
        2.senaryo: categoryRequest ile gelen Title Villa ve DB de VAR,   DB'de MEVCUT Title : Arsa            --> TRUE && TRUE     (UPDATE OLMAZ)
        3.senaryo: categoryRequest ile gelen Title Daire ama DB de YOK,  DB'de MEVCUT Title : Arsa            --> FALSE && TRUE    (UPDATE OLUR)    */
      //  }

        // DTO ===>>> POJO

        //category = categoryMapper.mapCategoryRequestDTOToCategory(categoryRequestDTO);

        category.setTitle(categoryRequestDTO.getTitle());
        category.setIcon(categoryRequestDTO.getIcon());
        category.setSeq(categoryRequestDTO.getSeq());
        category.setSlug(categoryRequestDTO.getSlug());
        category.setActive(categoryRequestDTO.isActive());

        Category updatedCategory = categoryRepository.save(category);

        return ResponseMessage.<CategoryResponseDTO>builder()
                .object(categoryMapper.mapCategoryToCategoryResponseDTO(updatedCategory))
                .message(SuccesMessages.CATEGORY_UPDATED_SUCCESS)
                .status(HttpStatus.OK)
                .build();
    }


    public ResponseMessage<CategoryResponseDTO> deleteCategory(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND + id));

        if (category.getBuiltIn()) {
            throw new ResourceNotFoundException(ErrorMessages.BUILT_IN_CATEGORY_CAN_NOT_BE_DELETED);
        }

        Advert advert = advertRepository.findAdvertByCategory(id);

        if (advert != null) {
            throw new RuntimeException(ErrorMessages.THIS_CATEGORY_CAN_NOT_BE_DELETED);
        }
        categoryRepository.deleteById(id);

        return ResponseMessage.<CategoryResponseDTO>builder()
                .object(categoryMapper.mapCategoryToCategoryResponseDTO(category))
                .message(SuccesMessages.CATEGORY_DELETED_SUCCESS)
                .status(HttpStatus.OK)
                .build();
    }


    public ResponseMessage<PropertyKeyResponse> createPropertyKey(Long id, PropertyKeyRequest propertyKeyRequest) {

        Category category = findCategoryById(id);

        Set<CategoryPropertyKey> existCategoryProperties = category.getCategoryPropertyKeys();

        for (CategoryPropertyKey key : existCategoryProperties) {
            if (key.getPropertyName().equals(propertyKeyRequest.getPropertyName())) {
                throw new ConflictException(ErrorMessages.PROPERTY_KEY_ALREADY_EXIST);
            }
        }
        CategoryPropertyKey categoryPropertyKey = new CategoryPropertyKey();
        categoryPropertyKey.setPropertyName(propertyKeyRequest.getPropertyName());
        categoryPropertyKey.setCategory(category);

        categoryPropertyKey = categoryPropertyKeyRepository.save(categoryPropertyKey);

        existCategoryProperties.add(categoryPropertyKey);

        category.setCategoryPropertyKeys(existCategoryProperties);
        categoryRepository.save(category);

        return ResponseMessage.<PropertyKeyResponse>builder()
                .object(categoryMapper.mapPropertyKeytoPropertyKeyResponse(categoryPropertyKey))
                .message(SuccesMessages.CATEGORY_PROPERTY_KEY_CREATED_SUCCESS)
                .status(HttpStatus.CREATED)
                .build();
    };


    public ResponseMessage<CategoryResponseDTO> findCategoryBySlug(String slug) {

        Category category = categoryRepository.findBySlug(slug).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND + slug));

        return ResponseMessage.<CategoryResponseDTO>builder()
                .object(categoryMapper.mapCategoryToCategoryResponseDTO(category))
                .message(SuccesMessages.RETURNED_CATEGORY_BY_SLUG_SUCCESS)
                .status(HttpStatus.OK)
                .build();
    }


    public List<Category> getCategoryByTitle(String category) {

        return categoryRepository.findByTitle(category).orElseThrow(
                () -> new BadRequestException(ErrorMessages.CATEGORY_NOT_FOUND)
        );
    }

    public int countBuiltInTrue() {
        return categoryRepository.countBuiltIn(true);

    }

    public List<Category> saveAll(List<Category> categories) {
        return categoryRepository.saveAll(categories);
    }

  /*  @Transactional
    public void resetCategoryTables() {
        categoryRepository.deleteByBuiltIn(false);
    }*/


    }

