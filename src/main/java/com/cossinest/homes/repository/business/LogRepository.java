package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log,Long> {

}
