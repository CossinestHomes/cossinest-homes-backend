package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Favorites;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorites,Long> {

    List<Favorites> findByUserId(AuthenticatedUsersResponse userId);

    @Query("SELECT f FROM Favorites f WHERE f.userId = :user_id")
    List<Favorites> findFavorites(@Param(value = "user_id") Long id);



}
