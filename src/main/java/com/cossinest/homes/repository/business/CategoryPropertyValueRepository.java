package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.CategoryPropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryPropertyValueRepository extends JpaRepository<CategoryPropertyValue,Long> {

    @Query("SELECT v FROM CategoryPropertyValue v WHERE value=?1")
    Optional<CategoryPropertyValue> findValueByName(Object obje);
}
