package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {


    @Query("SELECT COUNT(c) FROM City c")
    int countAllCities();

    @Query("SELECT c FROM City c WHERE c.country.id=:countryId")
    List<City> getByCity(@Param("countryId") Long countryId);

    List<City> findByNameIn(Set<String> cityNames);

//    @Query("SELECT c FROM City c WHERE LOWER(c.name) IN :names")
//    List<City> findByNameInIgnoreCase(@Param("names") Set<String> names);
}
