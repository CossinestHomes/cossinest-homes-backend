package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District , Long> {
    @Query("SELECT COUNT(d) FROM District d")
    int countAllDistricts();

    @Query("SELECT d FROM District d WHERE d.city.id=:cityId")
    List<District> getByDistrict(@Param("cityId") Long cityId);

}
