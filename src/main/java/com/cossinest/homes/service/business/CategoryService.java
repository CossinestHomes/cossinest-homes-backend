package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.CategoryRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.CategoryResponse;
import com.cossinest.homes.repository.business.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {



    @Autowired
    private CategoryRepository categoryRepository;





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


    public SuccesMessages updateCategory(Long id, CategoryRequest categoryRequest) {

        return categoryRepository.

    }
}
