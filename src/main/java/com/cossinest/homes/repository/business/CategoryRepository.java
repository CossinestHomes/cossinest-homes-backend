package com.cossinest.homes.repository.business;


import com.cossinest.homes.domain.concretes.business.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository <Category, Long> {




    boolean existsByTitle(String title);

    @Query("SELECT c from Category c WHERE c.isActive = true")

    Page<Category> findAllActiveCategories(Pageable pageable);






}