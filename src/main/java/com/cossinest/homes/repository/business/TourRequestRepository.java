package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TourRequestRepository extends JpaRepository<TourRequest,Long> {

    List<TourRequest> findAllByTourDate(LocalDate tourDate);

    @Query("SELECT t FROM TourRequest t WHERE (:createAt NULL OR t.createAt=:createAt) AND " +
            "(:tourTime NULL OR t.tourTime=:tourTime) AND" +
            "(:status NULL OR t.status=:status) AND" +
            "(:tourDate NULL OR t.tourDate=:tourDate)")
    Page<TourRequest> findAllByQuery(Pageable pageable,
                                     @Param("createAt") String createAt,
                                     @Param("tourTime") String tourTime,
                                     @Param("status") String status,
                                     @Param("tourDate") String tourDate);

}
