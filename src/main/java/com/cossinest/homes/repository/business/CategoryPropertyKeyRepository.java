package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface CategoryPropertyKeyRepository extends JpaRepository<CategoryPropertyKey, Long> {


    @Modifying
    @Transactional
    @Query("DELETE FROM CategoryPropertyKey c WHERE c.builtIn = :builtIn")
    void deleteByBuiltIn(@Param("builtIn") boolean builtIn);

    boolean existsByPropertyName(String propertyName);


    @Query("SELECT c FROM CategoryPropertyKey c WHERE c.category.id= ?1")
    Set<CategoryPropertyKey> findByCategory_Id(Long id);
}
