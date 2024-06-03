package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.repository.business.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {



    @Autowired
    private CategoryRepository categoryRepository;

    //advert için yardımcı method
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }


}
