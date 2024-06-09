package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.Favorites;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import com.cossinest.homes.repository.business.FavoritesRepository;
import com.cossinest.homes.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final AdvertService advertService;
    private final UserService userService;
    private final FavoritesRepository favoritesRepository;


    public List<AdvertResponse> getAuthenticatedUsersFavorites(HttpServletRequest httpServletRequest) {


        // Kullanıcının favori ilanlarını repositoryden çek:
        List<Favorites> favoriteList = favoritesRepository.findByUserId(userId);

        // Tüm ilanları al:
        List<Advert> allAdverts = advertService.getAllAdverts(); //advert service de mehmet hoca ile yazdıgımız method. ama bu kullanıslı olmadı bence

        // Favori ilanları AdvertResponse list e dönüştür:
        return favoriteList.stream()
                .map(favorite -> {
                    // Favori ilanın ID'sini al
                    Long advertId = (long) favorite.getAdvert_id();
                    // Tüm ilanlar arasında ilan ID'sine göre ilgili ilanı bul
                    Advert advert = allAdverts.stream()
                            .filter(advert1 -> advert1.getId().equals(advertId))
                            .findFirst()
                            .orElse(null);
                    // İlanı AdvertResponse'a dönüştür
                    return advert != null ? new AdvertResponse(advert) : null;
                })
                .filter(response -> response != null) // null olanları filtrele
                .collect(Collectors.toList());
    }


    public List<AdvertResponse> getUsersFavorites(Long userId) {
        // Kullanıcının favori ilanlarını repository üzerinden çek
        List<Favorites> favoriteList = favoritesRepository.findByUserId(userId);

        // Favori ilanları AdvertResponse listesine dönüştür
        return favoriteList.stream()
                .map(favorite -> new AdvertResponse(favorite.getAdvert_id()))
                .collect(Collectors.toList());

        //F:favori ilan yoksa bir mesaj vermeli miyim?
    }



}


