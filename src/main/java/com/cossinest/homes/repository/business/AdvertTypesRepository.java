package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.AdvertType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertTypesRepository extends JpaRepository<AdvertType , Long> {

    boolean findByTitle(String title);


}
