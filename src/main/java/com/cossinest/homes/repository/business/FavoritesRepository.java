package com.cossinest.homes.repository.business;

import com.cossinest.homes.domain.concretes.business.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<Favorites,Long> {



}
