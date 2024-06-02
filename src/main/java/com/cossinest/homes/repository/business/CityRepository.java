package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {
}
