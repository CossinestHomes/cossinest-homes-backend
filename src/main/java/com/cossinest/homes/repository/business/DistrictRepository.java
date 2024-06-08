package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District , Long> {
}
