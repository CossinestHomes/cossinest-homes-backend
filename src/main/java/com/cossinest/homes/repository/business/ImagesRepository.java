package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images,Long> {
}
