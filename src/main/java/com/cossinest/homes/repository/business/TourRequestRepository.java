package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.StatusType;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Repository
public interface TourRequestRepository extends JpaRepository<TourRequest,Long> {

    List<TourRequest> findAllByTourDate(LocalDate tourDate);


    @Query("SELECT t FROM TourRequest t WHERE ((t.guestUserId.id = :userId OR t.ownerUserId.id = :userId) " +
            "AND (:createAt IS NULL OR t.createAt = :createAt) " +
            "AND (:tourTime IS NULL OR t.tourTime = :tourTime) " +
            "AND (:status IS NULL OR t.status = :status) " +
            "AND (:tourDate IS NULL OR t.tourDate = :tourDate))")
    Page<TourRequest> findAllByQueryAuth(
            Pageable pageable,
            @Param("userId") Long userId,
            @Param("createAt") String createAt,
            @Param("tourTime") String tourTime,
            @Param("status") String status,
            @Param("tourDate") String tourDate
    );

    @Query("SELECT t FROM TourRequest t WHERE " +
            "(:createAt IS NULL OR t.createAt = :createAt) " +
            "AND (:tourTime IS NULL OR t.tourTime = :tourTime) " +
            "AND (:status IS NULL OR t.status = :status) " +
            "AND (:tourDate IS NULL OR t.tourDate = :tourDate) " +
            "ORDER BY t.tourDate ASC")
    Page<TourRequest> findAllByQueryAdmin(
            Pageable pageable,
            @Param("createAt") LocalDateTime createAt,
            @Param("tourTime") LocalTime tourTime,
            @Param("status") StatusType status,
            @Param("tourDate") LocalDate tourDate
    );

    @Query("SELECT t FROM TourRequest t WHERE (t.guestUserId.id=?1 OR  t.ownerUserId.id=?1) AND t.id=?2")
    TourRequest findByIdByCustomer(Long userId, Long tourRequestId);


    @Query("SELECT t FROM TourRequest t WHERE " +
            "(:date1 IS NULL OR :date2 IS NULL OR t.createAt BETWEEN :date1 AND :date2) AND " +
            "t.status = :statusType")
    List<TourRequest> getTourRequest(@Param("date1") LocalDateTime date1,
                                     @Param("date2") LocalDateTime date2,
                                     @Param("statusType") StatusType statusType);

    Set<TourRequest> findByIdIn(Set<Long> tourRequestIdList);
}
