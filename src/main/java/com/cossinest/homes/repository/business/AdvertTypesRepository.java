package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.AdvertType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertTypesRepository extends JpaRepository<AdvertType , Long> {

    boolean findByTitle(String title);


    @Modifying
    @Transactional
    @Query("DELETE FROM AdvertType a WHERE a.builtIn = :builtIn")
    void deleteByBuiltIn(@Param("builtIn") boolean builtIn);
}
