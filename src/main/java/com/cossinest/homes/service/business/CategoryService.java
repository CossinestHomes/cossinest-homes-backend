package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.*;

import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.payload.mappers.CategoryMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.request.business.AdvertRequest;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.business.CategoryPropKeyResponseDTO;
import com.cossinest.homes.payload.response.business.CategoryResponseDTO;
import com.cossinest.homes.repository.business.CategoryPropertyKeyRepository;
import com.cossinest.homes.repository.business.CategoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {



    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    CategoryPropertyKeyRepository categoryPropertyKeyRepository;



    //advert için yardımcı method
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById (Long id){
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException(ErrorMessages.CATEGORY_NOT_FOUND));
    }





    public Page<CategoryResponseDTO> getActiveCategoriesWithPage( int page, int size, String sort, Sort.Direction type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(type, sort));

        return categoryRepository.findAllActiveCategories(pageable);
    }


    public Page<CategoryResponseDTO> getAllCategoriesWithPage(int page, int size, String sort, Sort.Direction type) {

        Pageable pageable = PageRequest.of( page, size, Sort.by (type, sort) );

        return categoryRepository.findAllCategories(pageable);
    }

    public Category findCategory(Long id){
        return categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with id :" + id));
    }


    public CategoryResponseDTO findCategoryWithId(Long id) {

       Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with id :" + id));
        return categoryMapper.mapCategoryToCategoryResponseDTO(category);
    }


    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {

        if(categoryRepository.existsByTitle(categoryRequestDTO.getTitle())) {

            throw new ConflictException("Category " +categoryRequestDTO.getTitle() +" is already exist " );
        }
        Category category = categoryMapper.mapCategoryRequestDTOToCategory(categoryRequestDTO);

        Category createdCategory = categoryRepository.save(category);
        createdCategory.generateSlug();
        Category categoryresp = categoryRepository.save(createdCategory);
        return categoryMapper.mapCategoryToCategoryResponseDTO(categoryresp);

    }



    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {

        boolean existTitle = categoryRepository.existsByTitle(categoryRequestDTO.getTitle());  // categoryRequest ile gelen Title DB'de VAR mi?

        Category category = findCategory(id);

        if (existTitle && !categoryRequestDTO.getTitle().equals(category.getTitle())){

            throw new ConflictException("Title is already exist");

/*      1.senaryo: categoryRequest ile gelen Title Arsa,                 DB'de MEVCUT Title : Arsa            --> TRUE && FALSE    (UPDATE OLUR)
        2.senaryo: categoryRequest ile gelen Title Villa ve DB de VAR,   DB'de MEVCUT Title : Arsa            --> TRUE && TRUE     (UPDATE OLMAZ)
        3.senaryo: categoryRequest ile gelen Title Daire ama DB de YOK,  DB'de MEVCUT Title : Arsa            --> FALSE && TRUE    (UPDATE OLUR)    */
        }

        category.setTitle(categoryRequestDTO.getTitle());
        category.setIcon(categoryRequestDTO.getIcon());
        category.setSeq(categoryRequestDTO.getSeq());
        category.setSlug(categoryRequestDTO.getSlug());
        category.setActive(categoryRequestDTO.isActive());

        LocalDateTime updatedOn = LocalDateTime.now();
        category.setUpdatedAt(updatedOn);

        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.mapCategoryToCategoryResponseDTO(updatedCategory);
    }


    public CategoryResponseDTO deleteCategory(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with id :" + id));

        categoryRepository.deleteById(id);

        return categoryMapper.mapCategoryToCategoryResponseDTO(category);
    }


    public List<CategoryPropertyKey> findCategoryPropertyKeys(Long id) {

        Category category = findCategory(id);

        List <CategoryPropertyKey> categoryProperties = category.getCategoryPropertyKeys();
        return  categoryProperties;
    }


    public CategoryPropKeyResponseDTO createPropertyKey(Long id, String... keys) {


        Category category = findCategory(id);

        List <CategoryPropertyKey> categoryProperties = category.getCategoryPropertyKeys();
        CategoryPropertyKey categoryPropertyKey = new CategoryPropertyKey();

        for ( String key : keys){

            categoryPropertyKey.setName(key);
            categoryProperties.add(categoryPropertyKey);
        }
        categoryPropertyKeyRepository.save(categoryPropertyKey);

        return categoryMapper.mapCategPropKeyToCategPropKeyResponseDTO(categoryPropertyKey);
    }


    public CategoryResponseDTO findCategoryBySlug(String slug) {

        Category category = categoryRepository.findBySlug(slug).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with Slug :" + slug));

        return categoryMapper.mapCategoryToCategoryResponseDTO(category);
    }
}

