package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Favorites;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorites,Long> {



    @Query("SELECT f FROM Favorites f WHERE f.user.id = :userId")
    List<Favorites> findFavorites(@Param(value = "userId") Long userId);

    @Modifying
    @Query("DELETE FROM Favorites f WHERE f.user.id = :user_id AND f.advert.id = :advert_id")
    void deleteFavoriteIfExists(@Param("user_id") Long userId, @Param("advert_id") Long advertId);


    @Transactional
    @Modifying
    @Query("INSERT INTO Favorites (user, advert) SELECT u, a FROM User u, Advert a WHERE u.id = :user_id AND a.id = :advert_id")
    void addFavoriteIfNotExists(@Param("user_id") Long userId, @Param("advert_id") Long advertId);

    @Query("SELECT COUNT(f) > 0 FROM Favorites f WHERE f.user.id = :userId AND f.advert.id = :advertId")
    boolean existsByUserIdAndAdvertId(@Param("userId") Long userId, @Param("advertId") Long advertId);


    @Query("SELECT f FROM Favorites f WHERE f.user.id = :user_id")
    List<Favorites> findFavoritesByUserId(@Param("user_id") Long userId);

    @Modifying
    @Query("DELETE FROM Favorites f WHERE f.user.id = :user_id")
    void deleteAllByUserId(@Param("user_id") Long userId);


    @Query("SELECT f FROM Favorites f WHERE f.user.id=?1 AND f.advert.id=?2")
    Favorites getFavoriteByAdvertAndUser(Long id, Long advertId);
}
