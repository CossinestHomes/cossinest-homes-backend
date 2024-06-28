package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryPropertyKeyRepository extends JpaRepository<CategoryPropertyKey, Long> {



    boolean existsByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM CategoryPropertyKey c WHERE c.builtIn = :builtIn")
    void deleteByBuiltIn(@Param("builtIn") boolean builtIn);
}
