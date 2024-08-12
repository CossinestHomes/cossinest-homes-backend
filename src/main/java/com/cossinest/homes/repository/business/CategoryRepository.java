package com.cossinest.homes.repository.business;


import com.cossinest.homes.domain.concretes.business.Category;
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

    @Query("SELECT c FROM Category c WHERE c.slug=?1")
    Optional<Category> findBySlug(String slug);

    boolean existsByTitle(String title);

//    @Query("SELECT c FROM Category c WHERE c.active = true")
//    Page<Category> findAllActiveCategories(Pageable pageable);

    @Query("SELECT c FROM Category c WHERE :q IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')) ")
    Page<Category> findAll(@Param("q") String q,
                                Pageable pageable);


    //boolean existsByName(String name);


    Optional<List<Category>> findByTitle(String title);

    @Transactional
    @Modifying
    @Query("DELETE FROM Category c WHERE c.builtIn = ?1")
    void deleteByBuiltIn(boolean b);


    @Query("SELECT COUNT(b) FROM  Category b WHERE b.builtIn=?1")
    int countBuiltIn(boolean b);


//    @Query("SELECT c FROM Category c WHERE (:query IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')))")
//    Page<Category> findByActiveTrue(@Param("query") String query, Pageable pageable);

    @Query("SELECT c FROM Category c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Category> findByTitleContaining(@Param("query") String query, Pageable pageable);

    @Query("SELECT c FROM Category c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')) AND c.active = TRUE")
    Page<Category> findByTitleContainingAndIsActiveTrue(@Param("query") String query, Pageable pageable);

}
