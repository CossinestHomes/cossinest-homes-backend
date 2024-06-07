package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertRepository extends JpaRepository<Advert,Long> {
    @Query("SELECT a FROM Advert a WHERE " +
            "a.category.id = ?1 AND " +
            "a.advertType.id = ?2 AND " +
            "(a.price IS NULL OR a.price >= ?3) AND " +
            "(a.price IS NULL OR a.price <= ?4) AND " +
            "(a.status IS NULL OR a.status) = ?5 AND " +
            "(a.location IS NULL OR a.location=?6) AND" +
            "(a.title IS NULL OR a.title= ?7 OR a.desc=?7)")
    Page<Advert> findByAdvertByQuery(Long categoryId, int advertTypeId, Double priceStart, Double priceEnd, int status,String location,String query,Pageable pageable);

    @Query("SELECT a FROM Advert a WHERE a.user.id= ?1 ")
    Page<Advert> findAdvertsForUser(Long id, Pageable pageable);

    Optional<Advert> findBySlug(String slug);



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
}
