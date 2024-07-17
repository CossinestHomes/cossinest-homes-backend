package com.cossinest.homes.controller.business;


import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.business.CategoryPropKeyResponseDTO;
import com.cossinest.homes.payload.response.business.CategoryPropKeyssResponseDTO;
import com.cossinest.homes.payload.response.business.CategoryResponseDTO;
import com.cossinest.homes.service.business.CategoryPropertyKeyService;
import com.cossinest.homes.service.business.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {




    Logger logger = LoggerFactory.getLogger(CategoryController.class);


    private final CategoryService categoryService;
    private final CategoryPropertyKeyService categoryPropertyKeyService;



    // C 01     Tum AKTiF kategorileri Pageable yapida cagirma :


    @GetMapping("/active-categories")
    public Page<CategoryResponseDTO> getActiveCategoriesWithPage(

            @RequestParam("q") String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "20") int size,
            @RequestParam(value = "sort" , defaultValue = "category_id") String sort,
            @RequestParam(value = "type" , defaultValue = "ASC") String type) {

        return categoryService.getActiveCategoriesWithPage( q, page,  size, sort,  type);


    }



    // C 02     Tum kategorileri Pageable yapida cagirma :

    @GetMapping
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public Page<CategoryResponseDTO> getAllCategoriesWithPage(

            @RequestParam("q") String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "20") int size,
            @RequestParam(value = "sort" , defaultValue = "category_id") String sort,
            @RequestParam(value = "type" , defaultValue = "ASC") String type){

        return categoryService.getAllCategoriesWithPage(q, page,  size, sort,  type);


    }


    // C 03     id ile bir category cagirma (Path Variable ile) :

    @GetMapping("/{id}")
    public ResponseMessage<CategoryResponseDTO> getCategoryWithId(@PathVariable("id") Long id){

        return categoryService.findCategoryWithId(id);
    }



    // C 04     Category Objesi olusturma :

    @PostMapping
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO){

        return categoryService.createCategory(categoryRequestDTO);
    }


    // C 05 id ile category UPDATE etme (Path Variable ile) :

    @PutMapping("/{id}")                                // http://localhost:8080/categories/1  + PUT + JSON  // MESELA YANi...
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryResponseDTO> updateCategoryWithId(@PathVariable("id") Long id, @Valid  @RequestBody CategoryRequestDTO categoryRequestDTO){

        return categoryService.updateCategory(id, categoryRequestDTO);
    }


    // C 06 id ile category DELETE etme (Path Variable ile) :

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryResponseDTO> deleteCategory(@PathVariable("id") Long id ){

        return categoryService.deleteCategory(id);
    }


    // C07 id ile bir category'nin property key'lerini getirme (Path Variable ile) :

    @GetMapping("/{id}/properties")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryPropKeyssResponseDTO> getCategoryPropertyKeys(@PathVariable("id") Long id){

        return categoryService.findCategoryPropertyKeys(id);
    }


    // C08 id ile bir category'nin property key'ini olusturma (Path Variable ile) :

    @PostMapping("/{id}/properties")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryPropKeyResponseDTO> createPropertyKey(@PathVariable("id") Long id, @Valid  @RequestBody CategoryRequestDTO categoryRequestDTO, String... keys){

        return categoryService.createPropertyKey(id,  keys);
    }


    // C09 id ile property key'i UPDATE etme  (Path Variable ile) :

    @PutMapping("/properties/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryPropKeyResponseDTO> updateCatPropertyKey(@PathVariable("id") Long id, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO){


        return categoryPropertyKeyService.updatePropertyKey (id, categoryRequestDTO);
    }


    // C10 id ile property key'i DELETE etme (Silme)  (Path Variable ile) :

    @DeleteMapping("/properties/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryPropKeyResponseDTO> deleteCategPropertyKey(@PathVariable("id") Long id ) {

        return categoryPropertyKeyService.deletePropertyKey(id);
    }


    // C11 SLUG ile Category cagirma  (Path Variable ile) :

    @GetMapping("/{slug}/category")
    public ResponseMessage<CategoryResponseDTO> getCategoryBySlug(@PathVariable("slug") String slug){

        return categoryService.findCategoryBySlug(slug);


    }






}
