package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Favorites;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorites,Long> {



    @Query("SELECT f FROM Favorites f WHERE f.user.id = :user_id")
    List<Favorites> findFavorites(@Param(value = "user_id") Long id);

    @Query("DELETE FROM Favorites f WHERE f.user.id = :user_id AND f.advert.id = :advert_id")
    void deleteFavoriteIfExists(@Param("user_id") Long userId, @Param("advert_id") Long advertId);

    @Query("INSERT INTO Favorites (user, advert) SELECT u, i FROM User u, Item i WHERE u.id = :user_id AND i.id = :advert_id")
    void addFavoriteIfNotExists(@Param("user_id") Long userId, @Param("advert_id") Long advertId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Favorites f WHERE f.user.id = :userId AND f.advert.id = :advertId")
    boolean existsByUserIdAndAdvertId(@Param("userId") Long userId, @Param("advertId") Long advertId);


    @Query("SELECT f FROM Favorites f WHERE f.user.id = :user_id")
    List<Favorites> findFavoritesByUserId(@Param("user_id") Long userId);

    @Query("DELETE FROM Favorites f WHERE f.user.id = :user_id")
    void deleteAllByUserId(@Param("user_id") Long userId);
}
