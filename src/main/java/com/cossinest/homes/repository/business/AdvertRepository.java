package com.cossinest.homes.repository.business;

import aj.org.objectweb.asm.commons.Remapper;
import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AdvertRepository extends JpaRepository<Advert,Long> {

   /* @Query("SELECT a FROM Advert a WHERE " +
            "a.category.id = ?1 AND " +
            "a.advertType.id = ?2 AND " +
            "(?3 IS NULL OR ?4 IS NULL OR a.price BETWEEN ?3 AND ?4) AND " +
            "(?5 IS NULL OR a.status = ?5) AND " +
            "(?6 IS NULL OR a.location = ?6) AND " +
            "(?7 IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%', ?7, '%')) OR LOWER(a.desc) LIKE LOWER(CONCAT('%', ?7, '%')))")
    Page<Advert> findByAdvertByQuery(Long categoryId, Long advertTypeId, Double priceStart, Double priceEnd, Integer status, String location, String query, Pageable pageable);
*/


    /*@Query("SELECT a FROM Advert a WHERE " +
            "(?1 IS NULL OR a.categoryId = ?1) AND " +
            "(?2 IS NULL OR a.advertTypeId = ?2) AND " +
            "(?3 IS NULL OR a.price >= ?3) AND " +
            "(?4 IS NULL OR a.price <= ?4) AND " +
            "(?5 IS NULL OR a.status = ?5) AND " +
            "(?6 IS NULL OR a.location LIKE %?6%) AND " +
            "(?7 IS NULL OR a.query LIKE %?7%)")
    Page<Advert> findByAdvertByQuery(Long categoryId, Long advertTypeId, Double priceStart, Double priceEnd, Integer status, String location, String query, Pageable pageable);
*/
    @Query("SELECT a FROM Advert a WHERE " +
            "(:query IS NULL OR a.description LIKE %:query% OR a.title LIKE %:query% ) " +
            "AND (:categoryId IS NULL OR a.category.id = :categoryId) " +
            "AND (:advertTypeId IS NULL OR a.advertType.id = :advertTypeId) " +
            "AND (:priceStart IS NULL OR a.price >= :priceStart) " +
            "AND (:priceEnd IS NULL OR a.price <= :priceEnd)" +
            "AND (:status IS NULL OR a.status = :status)")
    Page<Advert> findByAdvertByQuery(
            @Param("query") String query,
            @Param("categoryId") Long categoryId,
            @Param("advertTypeId") Long advertTypeId,
            @Param("priceStart") Double priceStart,
            @Param("priceEnd") Double priceEnd,
             @Param("status")Integer status,
            Pageable pageable
    );



    @Query("SELECT a FROM Advert a WHERE a.user.id= ?1 ")
    Page<Advert> findAdvertsForUser(Long id, Pageable pageable);

    Optional<Advert> findBySlug(String slug);

    @Query("SELECT a FROM Advert a WHERE (:date1 IS NULL OR :date2 IS NULL OR  a.createdAt BETWEEN:date1 AND :date2) AND " +
            "(:category IS NULL OR a.category.title =:category) AND (:type IS NULL OR a.advertType.title=:type) AND " +
            "(:enumStatus IS NULL OR a.status=:enumStatus)")
    Optional<List<Advert>> findByQuery(@Param(value = "date1") LocalDateTime date1,
                                       @Param(value = "date2") LocalDateTime date2,
                                       @Param(value = "category")String category,
                                       @Param(value = "type") String type,
                                       @Param(value = "enumStatus") int enumStatus);


    @Query("SELECT a FROM Advert a WHERE SIZE(a.tourRequestList) > :amount ORDER BY SIZE(a.tourRequestList) DESC")
    Page<Advert> getMostPopulerAdverts(@Param("amount") int amount, Pageable pageable);

   /* @Modifying
    @Transactional
    @Query("DELETE FROM Advert a WHERE a.builtIn = :builtIn")*/
  //  void deleteByBuiltIn(/*@Param("builtIn")*/ boolean builtIn);




    /*
        @Query("SELECT u FROM User u WHERE (:name IS NULL OR u.firstName=:name)AND" +
            "(:surname IS NULL OR u.lastName=:surname)AND" +
            "(:email IS NULL OR u.email=:email)OR" +
            "(:phone IS NULL OR u.phone=:phone)")
    Page<User> findAll(@Param(value = "name") String name,
                       @Param(value = "surname") String surname,
                       @Param(value = "email") String email,
                       @Param(value = "phone") String phone,
                       Pageable pageable);

    * */

    @Query("SELECT a FROM Advert a WHERE " +
            "a.category.id = :categoryId ")
    Advert findAdvertByCategory(Long categoryId);

    Set<Advert> findByIdIn(Set<Long> advertIdList);

    @Transactional
    @Modifying
    @Query("DELETE FROM Advert a WHERE a.builtIn = ?1")
    void deleteByBuiltIn(boolean b);

}
