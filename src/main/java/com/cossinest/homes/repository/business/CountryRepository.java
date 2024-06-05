package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {


    List<Country> findAll(Country country);


}
