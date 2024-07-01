package com.cossinest.homes.repository.business;


import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.payload.response.business.CategoryResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository <Category, Long> {



    Optional<Category> findBySlug(String slug);

    boolean existsByTitle(String title);

    @Query("SELECT c FROM Category c WHERE c.active = true")
    Page<CategoryResponseDTO> findAllActiveCategories(Pageable pageable);

    @Query("SELECT c FROM Category c")
    Page<CategoryResponseDTO> findAllCategories(Pageable pageable);

    //boolean existsByName(String name);

    Optional<List<Category>> findByTitle(String title);



    @Modifying
    @Transactional
    @Query("DELETE FROM Category c WHERE c.builtIn = :builtIn")
    void deleteByBuiltIn(@Param("builtIn") boolean builtIn);

}
