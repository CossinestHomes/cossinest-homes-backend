package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TourRequestRepository extends JpaRepository<TourRequest,Long> {

    List<TourRequest> findAllByTourDate(LocalDate tourDate);


    @Query("SELECT (COUNT(t) > 0) FROM TourRequest t WHERE t.tourDate=?1 AND EXTRACT(HOUR FROM t.tourTime)=?2")
    Boolean isExistsOnDateByTime(LocalDate tourDate, LocalDateTime tourTime);
}
