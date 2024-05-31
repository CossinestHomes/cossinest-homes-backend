package com.cossinest.homes.service.business;

import com.cossinest.homes.repository.business.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {



    @Autowired
    private CategoryRepository categoryRepository;


}
