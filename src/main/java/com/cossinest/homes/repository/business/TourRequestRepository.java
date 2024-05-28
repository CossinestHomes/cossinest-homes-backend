package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.TourRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRequestRepository extends JpaRepository<TourRequest,Long> {

}
