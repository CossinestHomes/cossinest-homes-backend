package com.cossinest.homes.geoNames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoNamesCountryRepository extends JpaRepository<GeoNamesCountry, Long> {
}
