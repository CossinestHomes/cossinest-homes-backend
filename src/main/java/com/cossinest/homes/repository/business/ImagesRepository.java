package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesRepository extends JpaRepository<Images,Long> {
    List<Images> findByAdvertId(Long advertId);
}
