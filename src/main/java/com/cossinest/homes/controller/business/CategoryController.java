package com.cossinest.homes.controller.business;



import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.payload.request.business.PropertyKeyRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.*;
import com.cossinest.homes.service.business.CategoryPropertyKeyService;
import com.cossinest.homes.service.business.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;
    private final CategoryPropertyKeyService categoryPropertyKeyService;

    // C 01     Tum AKTiF kategorileri Pageable yapida cagirma :

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> getActiveCategoriesWithPage(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "catId") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type) {

        Page<CategoryResponseDTO> categoryList= categoryService.getActiveCategoriesWithPage(query,page,size,sort,type);

        return ResponseEntity.ok(categoryList) ;


    }

    // C 02     Tum kategorileri Pageable yapida cagirma :

    @GetMapping("/getCategoriesForManagers")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public Page<CategoryResponseDTO> getAllCategoriesWithPage(
            @RequestParam(value = "q") String query,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type){

        return categoryService.getAllCategoriesWithPage(query ,page,  size, sort,  type);


    }


    // C 03     id ile bir category cagirma (Path Variable ile) :

    @GetMapping("/{id}")
    public ResponseMessage<CategoryResponseDTO> getCategoryWithId(@PathVariable("id") Long id){

        return categoryService.findCategoryWithId(id);
    }



    // C 04     Category Objesi olusturma :

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO){

        return categoryService.createCategory(categoryRequestDTO);
    }


    // C 05 id ile category UPDATE etme (Path Variable ile) :

    @PutMapping("/{id}")  // http://localhost:8080/categories/1  + PUT + JSON  // MESELA YANi...
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryResponseDTO> updateCategoryWithId(@PathVariable("id") Long id, @Valid  @RequestBody CategoryRequestDTO categoryRequestDTO){

        return categoryService.updateCategory(id, categoryRequestDTO);
    }


    // C 06 id ile category DELETE etme (Path Variable ile) :

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryResponseDTO> deleteCategory(@PathVariable("id") Long id ){

        return categoryService.deleteCategory(id);
    }


     //C07 id ile bir category'nin property key'lerini getirme (Path Variable ile) :

    @GetMapping("/{id}/properties")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public Set<PropertyKeyResponse> getCategoryPropertyKeys(@PathVariable("id") Long id){

        return categoryPropertyKeyService.findByCategoryIdEquals(id);
    }


    // C08 id ile bir category'nin property key'ini olusturma (Path Variable ile) :

    @PostMapping("/{id}/properties")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<PropertyKeyResponse> createPropertyKey(@PathVariable("id") Long id, @Valid  @RequestBody PropertyKeyRequest propertyKeyRequest){

        return categoryService.createPropertyKey(id, propertyKeyRequest);
    }


    // C09 id ile property key'i UPDATE etme  (Path Variable ile) :

    @PutMapping("/properties/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<PropertyKeyResponse> updatePropertyKey(@PathVariable("id") Long id, @Valid @RequestBody PropertyKeyRequest propertyKeyRequest){

        return categoryPropertyKeyService.updatePropertyKey (id, propertyKeyRequest);
    }


    // C10 id ile property key'i DELETE etme (Silme)  (Path Variable ile) :

    @DeleteMapping("/properties/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<PropertyKeyResponse> deleteCategoryPropertyKey(@PathVariable("id") Long id ) {

        return categoryPropertyKeyService.deletePropertyKey(id);
    }


    // C11 SLUG ile Category cagirma  (Path Variable ile) :

    @GetMapping("/slug/{slug}")
    public ResponseMessage<CategoryResponseDTO> getCategoryBySlug(@PathVariable("slug") String slug){

        return categoryService.findCategoryBySlug(slug);

    }


}
