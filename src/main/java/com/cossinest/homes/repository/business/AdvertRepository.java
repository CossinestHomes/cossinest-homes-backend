package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertRepository extends JpaRepository<Advert,Long> {
    @Query("SELECT a FROM Advert a WHERE a.categoryId = ?1 AND a.advertTypeId = ?2 AND a.price >= ?3 AND a.price <= ?4 AND a.status = ?5 AND a.location=?6")
    Page<Advert> findByAdvertByQuery(Long categoryId, int advertTypeId, Double priceStart, Double priceEnd, int status,String location ,Pageable pageable);

}
