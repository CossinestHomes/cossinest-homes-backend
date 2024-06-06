package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Category;

import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.CategoryRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.CategoryResponse;
import com.cossinest.homes.repository.business.CategoryRepository;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {



    @Autowired
    private CategoryRepository categoryRepository;

    //advert için yardımcı method
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById (Long id){
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException(ErrorMessages.CATEGORY_NOT_FOUND));
    }





    public Page<Category> getActiveCategoriesWithPage( int page, int size, String sort, Sort.Direction type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(type, sort));

        return categoryRepository.findAllActiveCategories(pageable);
    }


    public Page<Category> getAllCategoriesWithPage(int page, int size, String sort, Sort.Direction type) {

        Pageable pageable = PageRequest.of( page, size, Sort.by (type, sort) );

        return categoryRepository.findAll(pageable);
    }


    public Category findCategory(Long id) {

        return categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with id :" + id));
    }


    public void createCategory(Category category) {

        if(categoryRepository.existsByTitle(category.getTitle())) {

            throw new ConflictException("Category " +category.getTitle() +" is already exist " );
        }
        categoryRepository.save(category);
    }


    public void updateCategory(Long id, CategoryRequest categoryRequest) {

        boolean existTitle = categoryRepository.existsByTitle(categoryRequest.getTitle());  // categoryRequest ile gelen Title DB'de VAR mi?

        Category category = findCategory(id);

        if (existTitle && !categoryRequest.getTitle().equals(category.getTitle())){

            throw new ConflictException("Title is already exist");

/*      1.senaryo: categoryRequest ile gelen Title Arsa,                 DB'de MEVCUT Title : Arsa            --> TRUE && FALSE    (UPDATE OLUR)
        2.senaryo: categoryRequest ile gelen Title Villa ve DB de VAR,   DB'de MEVCUT Title : Arsa            --> TRUE && TRUE     (UPDATE OLMAZ)
        3.senaryo: categoryRequest ile gelen Title Daire ama DB de YOK,  DB'de MEVCUT Title : Arsa            --> FALSE && TRUE    (UPDATE OLUR)    */

        }

        category.setTitle(categoryRequest.getTitle());
        category.setIcon(categoryRequest.getIcon());
        category.setSeq(categoryRequest.getSeq());
        category.setSlug(categoryRequest.getSlug());
        category.setActive(categoryRequest.isActive());

        LocalDateTime updatedOn = LocalDateTime.now();
        category.setUpdatedAt(updatedOn);

        categoryRepository.save(category);

    }


    public void deleteCategory(Long id) {

        Category category = findCategory(id);
        categoryRepository.delete(category);
    }
}
