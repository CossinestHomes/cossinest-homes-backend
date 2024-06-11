package com.cossinest.homes.repository.business;


import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository <Category, Long> {




    boolean existsByTitle(String title);

    @Query("SELECT c from Category c WHERE c.active = true")
    Page<Category> findAllActiveCategories(Pageable pageable);


    @Query("SELECT c from CategoryPropertyKey c WHERE c.propertyKeyId = pKeyId")
    CategoryPropertyKey findByPropertyKeyId(@Param("pKeyId") Long propertyKeyId);


    boolean existsByName(String name);

    Optional<List<Category>> findByTitle(String title);
}
