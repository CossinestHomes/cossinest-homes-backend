package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {


    @Query("SELECT COUNT(c) FROM City c")
    int countAllCities();
}
