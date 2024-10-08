package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.AdvertType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertTypesRepository extends JpaRepository<AdvertType , Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM AdvertType a WHERE a.builtIn = ?1")
    void deleteByBuiltIn(boolean b);

    boolean existsByTitle(String title);

    Optional<AdvertType>  findByTitle(String type);
}
