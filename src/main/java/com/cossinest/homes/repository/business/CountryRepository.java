package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {


    @Query("SELECT COUNT(c) FROM Country c")
    int cuntAllCountries();
}
