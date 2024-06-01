package com.cossinest.homes.controller.business;


import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.service.business.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import java.util.List;


@RestController
@RequestMapping("/categories")
public class CategoryController {



    Logger logger = LoggerFactory.getLogger(CategoryController.class);


    @Autowired
    private CategoryService categoryService;




    // C 01     Tum AKTiF kategorileri Pageable yapida cagirma :

    @GetMapping
    public ResponseEntity<Page<Category>> getActiveCategoriesWithPage(
            @RequestParam("q") String query,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestParam("type") Sort.Direction type) {

        Page<Category> categoryPage = categoryService.getActiveCategoriesWithPage( page,  size, sort,  type);

        return ResponseEntity.ok(categoryPage);
    }



    // C 02     Tum kategorileri Pageable yapida cagirma :
    @GetMapping("/admin")
    public ResponseEntity<Page<Category>> getAllCategoriesWithPage(

            @RequestParam("q") String query,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestParam("type") Sort.Direction type
    ){
        Page<Category> categoryPage = categoryService.getAllCategoriesWithPage(page,  size, sort,  type);

        return ResponseEntity.ok(categoryPage);
    }


    // C 03     id ile bir category cagirma (Path Variable ile) :

    @GetMapping("{id}")
    public ResponseEntity<Category> getCategoryWithId(@PathVariable("id") Long id){

        Category category = categoryService.findCategory(id);
        return  ResponseEntity.ok(category);
    }


    // C 04     Category Objesi olusturma :

    @PostMapping
    public ResponseEntity<Map<String, String>> createCategory(@Valid @RequestBody Category category){

        categoryService.createCategory(category);

        Map <String, String> map = new HashMap<>();     //sadece bu METOD'un icinde kullanilacagi icin map OBJECT'ini NEW'leyerek olusturduk

        map.put("message", "Category is created successfuly");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }


    // C 05 id ile UPDATE etme :









}
