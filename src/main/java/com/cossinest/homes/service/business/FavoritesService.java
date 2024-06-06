package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    //private final AdvertRepository advertRepository;
    //private final UserRepository userRepository;
    public List<AdvertResponse> getAuthenticatedUsersFavorites(int userId) {
        // Belirli bir kullanıcıya ait favori ilanları al
        List<Favorites> favorites = favoritesRepository.findByUserId(userId);

        // Favori ilanlarını AdvertResponse listesine dönüştür
        List<AdvertResponse> advertResponses = favorites.stream()
                .map(favorite -> mapToFavoriResponse(favorite)) // Favori ilanları AdvertResponse'e dönüştürülür
                .collect(Collectors.toList());

        return advertResponses;
    }

    // Favorites entity'sini AdvertResponse'e dönüştüren yardımcı metot
    private AdvertResponse mapToFavoriResponse(Favorites favorite) {
        AdvertResponse advertResponse = new AdvertResponse();
        advertResponse.setId(favorite.getAdvert_id()); // Favori ilanın ID'sini set et
        // Diğer gerekli alanları set et (örneğin, ilan başlığı, fiyatı, vb.)
        return advertResponse;
    }
}


