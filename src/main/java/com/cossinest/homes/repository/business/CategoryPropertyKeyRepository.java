package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryPropertyKeyRepository extends JpaRepository<CategoryPropertyKey, Long> {


    boolean existsByName(String name);
}
