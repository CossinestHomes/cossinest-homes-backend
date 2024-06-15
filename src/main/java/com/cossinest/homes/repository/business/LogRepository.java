package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log,Long> {

   // @Query("SELECT a FROM Log a WHERE (:date1 IS NULL OR :date2 IS NULL OR  a.createdAt BETWEEN(:date1 AND :date2)) AND " +
   //         "(:category IS NULL OR a.category.title =:category) AND (:type IS NULL OR a.advertType.title=:type) AND " +
    //        "(:enumStatus IS NULL OR a.status=:enumStatus)")
   // void getAdvertsReport(String date1, String date2, String category, String type, String status);
}
