package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.*;


import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.payload.mappers.CategoryMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;

import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.AdvertRequest;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.AdvertResponse;

import com.cossinest.homes.payload.response.business.CategoryPropKeyResponseDTO;
import com.cossinest.homes.payload.response.business.CategoryPropKeyssResponseDTO;
import com.cossinest.homes.payload.response.business.CategoryResponseDTO;
import com.cossinest.homes.repository.business.AdvertRepository;
import com.cossinest.homes.repository.business.CategoryPropertyKeyRepository;
import com.cossinest.homes.repository.business.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service                            // @Service anot icinde @Component anot. da var
public class CategoryService {



    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private final AdvertRepository advertRepository;




    //advert için yardımcı method
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById (Long id){
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException(ErrorMessages.CATEGORY_NOT_FOUND));
    }





    public Page<CategoryResponseDTO> getActiveCategoriesWithPage( String q, int page, int size, String sort, String type) {


        Pageable pageable= PageRequest.of(page, size, Sort.by(String.valueOf(sort)).ascending());

        if(Objects.equals(type, "DESC")){
            pageable=PageRequest.of(page, size, Sort.by(String.valueOf(sort)).descending());
        }
        return categoryRepository.findAllActiveCategories(pageable);
    }


    public Page<CategoryResponseDTO> getAllCategoriesWithPage(String q, int page, int size, String sort, String type) {

        Pageable pageable= PageRequest.of(page, size, Sort.by(String.valueOf(sort)).ascending());

        if(Objects.equals(type, "DESC")){
                                            pageable=PageRequest.of(page, size, Sort.by(String.valueOf(sort)).descending());
                                            }
        return categoryRepository.findAllCategories(pageable);
    }


    public Category findCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with id :" + id));
    }


    public ResponseMessage<CategoryResponseDTO> findCategoryWithId(Long id) {

       Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with id :" + id));

        return ResponseMessage.<CategoryResponseDTO> builder()
                .object(categoryMapper.mapCategoryToCategoryResponseDTO(category))
                .message(SuccesMessages.RETURNED_A_CATEGORY)
                .status(HttpStatus.OK)
                .build();
    }


    public ResponseMessage<CategoryResponseDTO> createCategory(CategoryRequestDTO categoryRequestDTO) {

        if(categoryRepository.existsByTitle(categoryRequestDTO.getTitle())) {

            throw new ConflictException("Category " +categoryRequestDTO.getTitle() +" is already exist " );
        }

        Category category = categoryMapper.mapCategoryRequestDTOToCategory(categoryRequestDTO);

        Category createdCategory = categoryRepository.save(category);
        createdCategory.generateSlug();
        Category categoryResponse = categoryRepository.save(createdCategory);

        return ResponseMessage.<CategoryResponseDTO> builder()
                .object(categoryMapper.mapCategoryToCategoryResponseDTO(categoryResponse))
                .message(SuccesMessages.CATEGORY_CREATED_SUCCESS)
                .status(HttpStatus.CREATED)
                .build();
    }



    public ResponseMessage<CategoryResponseDTO> updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {

        boolean existTitle = categoryRepository.existsByTitle(categoryRequestDTO.getTitle());  // categoryRequest ile gelen Title DB'de VAR mi?

        Category category = findCategoryById(id);


        if(category.getBuiltIn()){
            throw new ResourceNotFoundException("This category cannot be updated");
        }

        if (existTitle && !categoryRequestDTO.getTitle().equals(category.getTitle())){

            throw new ConflictException("Title is already exist");

/*      1.senaryo: categoryRequest ile gelen Title Arsa,                 DB'de MEVCUT Title : Arsa            --> TRUE && FALSE    (UPDATE OLUR)
        2.senaryo: categoryRequest ile gelen Title Villa ve DB de VAR,   DB'de MEVCUT Title : Arsa            --> TRUE && TRUE     (UPDATE OLMAZ)
        3.senaryo: categoryRequest ile gelen Title Daire ama DB de YOK,  DB'de MEVCUT Title : Arsa            --> FALSE && TRUE    (UPDATE OLUR)    */
        }

        // DTO ===>>> POJO

        category = categoryMapper.mapCategoryRequestDTOToCategory(categoryRequestDTO);

        Category updatedCategory = categoryRepository.save(category);

        return ResponseMessage.<CategoryResponseDTO> builder()
                .object(categoryMapper.mapCategoryToCategoryResponseDTO(updatedCategory))
                .message(SuccesMessages.CATEGORY_UPDATED_SUCCESS)
                .status(HttpStatus.OK)
                .build();
    }


    public ResponseMessage<CategoryResponseDTO> deleteCategory(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with id :" + id));

        if(category.getBuiltIn()){
            throw new ResourceNotFoundException("This category cannot be deleted");
        }

       Advert advert = advertRepository.findAdvertByCategory(id);

        if (advert!=null){
            throw new RuntimeException("This category cannot be deleted");
        }
        categoryRepository.deleteById(id);

        return ResponseMessage.<CategoryResponseDTO> builder()
                .object(categoryMapper.mapCategoryToCategoryResponseDTO(category))
                .message(SuccesMessages.CATEGORY_DELETED_SUCCESS)
                .status(HttpStatus.OK)
                .build();
    }


    public ResponseMessage<CategoryPropKeyssResponseDTO> findCategoryPropertyKeys(Long id) {

        Category category = findCategoryById(id);

        List<CategoryPropertyKey> categoryProperKeys = category.getCategoryPropertyKeys();

        return ResponseMessage.<CategoryPropKeyssResponseDTO> builder()
                .object(categoryMapper.mapCategPropKeyssToCategPropKeyssResponseDTO(categoryProperKeys))
                .message(SuccesMessages.RETURNED_A_CATEGORY_PROPERTY_KEYS)
                .status(HttpStatus.OK)
                .build();
    }




    public  ResponseMessage<CategoryPropKeyResponseDTO> createPropertyKey(Long id, String... keys) {


        Category category = findCategoryById(id);

        List <CategoryPropertyKey> categoryProperties = category.getCategoryPropertyKeys();
        CategoryPropertyKey categoryPropertyKey = new CategoryPropertyKey();

        for ( String key : keys){

            categoryPropertyKey.setName(key);
            categoryProperties.add(categoryPropertyKey);
        }
        categoryPropertyKeyRepository.save(categoryPropertyKey);

        return ResponseMessage.<CategoryPropKeyResponseDTO> builder()
                .object(categoryMapper.mapCategPropKeyToCategPropKeyResponseDTO(categoryPropertyKey))
                .message(SuccesMessages.CATEGORY_PROPERTY_KEY_CREATED_SUCCESS)
                .status(HttpStatus.CREATED)
                .build();
    }


    public ResponseMessage<CategoryResponseDTO> findCategoryBySlug(String slug) {

        Category category = categoryRepository.findBySlug(slug).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with Slug :" + slug));

        return ResponseMessage.<CategoryResponseDTO> builder()
                .object(categoryMapper.mapCategoryToCategoryResponseDTO(category))
                .message(SuccesMessages.RETURNED_CATEGORY_BY_SLUG_SUCCESS)
                .status(HttpStatus.OK)
                .build();
    }



    public List<Category> getCategoryByTitle(String category) {

   return categoryRepository.findByTitle(category).orElseThrow(
              ()-> new BadRequestException(ErrorMessages.CATEGORY_NOT_FOUND)
      );
    }

  /*  @Transactional
    public void resetCategoryTables() {
        categoryRepository.deleteByBuiltIn(false);
    }*/
}

