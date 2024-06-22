package com.cossinest.homes.controller.business;


import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.business.CategoryPropKeyResponseDTO;
import com.cossinest.homes.payload.response.business.CategoryResponseDTO;
import com.cossinest.homes.service.business.CategoryPropertyKeyService;
import com.cossinest.homes.service.business.CategoryService;
import jakarta.validation.Valid;
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
@RequestMapping("/categories")
public class CategoryController {




    Logger logger = LoggerFactory.getLogger(CategoryController.class);


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryPropertyKeyService categoryPropertyKeyService;


//@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")

    // C 01     Tum AKTiF kategorileri Pageable yapida cagirma :

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> getActiveCategoriesWithPage(
            @RequestParam("q") String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "20") int size,
            @RequestParam(value = "sort" , defaultValue = "category_id") String sort,
            @RequestParam(value = "type" , defaultValue = "ASC") String type) {

        Page<CategoryResponseDTO> categoryResponseDTOPage = categoryService.getActiveCategoriesWithPage( q, page,  size, sort,  type);

        return ResponseEntity.ok(categoryResponseDTOPage);
    }



    // C 02     Tum kategorileri Pageable yapida cagirma :

    @GetMapping("/admin")
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategoriesWithPage(

            @RequestParam("q") String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "20") int size,
            @RequestParam(value = "sort" , defaultValue = "category_id") String sort,
            @RequestParam(value = "type" , defaultValue = "ASC") String type){

        Page<CategoryResponseDTO> categoryResponseDTOPage = categoryService.getAllCategoriesWithPage(q, page,  size, sort,  type);

        return ResponseEntity.ok(categoryResponseDTOPage);
    }


    // C 03     id ile bir category cagirma (Path Variable ile) :

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryWithId(@PathVariable("id") Long id){

        CategoryResponseDTO categoryResponseDTO = categoryService.findCategoryWithId(id);

        return  ResponseEntity.ok(categoryResponseDTO);
    }


    // C 04     Category Objesi olusturma :

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){

        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);

        return ResponseEntity.ok(categoryResponseDTO);
    }


    // C 05 id ile category UPDATE etme (Path Variable ile) :


    @PutMapping("{id}")         // http://localhost:8080/categories/1  + PUT + JSON  // MESELA YANi...
    public ResponseEntity<CategoryResponseDTO> updateCategoryWithId(@PathVariable("id") Long id, @Valid  @RequestBody CategoryRequestDTO categoryRequestDTO){

        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(id, categoryRequestDTO);

        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }


    // C 06 id ile category DELETE etme (Path Variable ile) :

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable("id") Long id ){

        CategoryResponseDTO categoryResponseDTO = categoryService.deleteCategory(id);

        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }


    // C07 id ile bir category'nin property key'lerini getirme (Path Variable ile) :

    @GetMapping("/{id}/properties")
    public ResponseEntity<List<CategoryPropertyKey>> getCategoryPropertyKeys(@PathVariable("id") Long id){

        List<CategoryPropertyKey> categoryProps = categoryService.findCategoryPropertyKeys(id);
        return ResponseEntity.ok(categoryProps);
    }


    // C08 id ile bir category'nin property key'ini olusturma (Path Variable ile) :

    @PostMapping("/{id}/properties")
    public ResponseEntity<CategoryPropKeyResponseDTO> createPropertyKey(@PathVariable("id") Long id, @Valid  @RequestBody CategoryRequestDTO categoryRequestDTO, String... keys){

        CategoryPropKeyResponseDTO categoryPropKeyResponseDTO = categoryService.createPropertyKey(id,  keys);

        return new ResponseEntity<>(categoryPropKeyResponseDTO, HttpStatus.CREATED);

    }

    // C09 id ile property key'i UPDATE etme  (Path Variable ile) :

    @PutMapping("/properties/{id}")
    public ResponseEntity<CategoryPropertyKey> updateCatPropertyKey(@PathVariable("id") Long id, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO){

        CategoryPropertyKey  updatedPropertyKey = categoryPropertyKeyService.updatePropertyKey (id, categoryRequestDTO);

        return ResponseEntity.ok(updatedPropertyKey);
    }


    // C10 id ile property key'i DELETE etme (Silme)  (Path Variable ile) :

    @DeleteMapping("/properties/{id}")
    public ResponseEntity<CategoryPropertyKey> deleteCatPropertyKey(@PathVariable("id") Long id ) {

        CategoryPropertyKey deletedPropertyKey = categoryPropertyKeyService.deletePropertyKey(id);

        return ResponseEntity.ok(deletedPropertyKey);
    }


    // C11 SLUG ile Category cagirma  (Path Variable ile) :

    @GetMapping("/{slug}")
    public ResponseEntity<CategoryResponseDTO> getCategoryBySlug(@PathVariable("slug") String slug){

        CategoryResponseDTO categoryResponseDTO = categoryService.findCategoryBySlug(slug);

        return  ResponseEntity.ok(categoryResponseDTO);
    }






}
