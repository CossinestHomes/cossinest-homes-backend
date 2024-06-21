package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.Favorites;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.AdvertMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.AdvertRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import com.cossinest.homes.repository.business.FavoritesRepository;
import com.cossinest.homes.repository.user.UserRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final AdvertService advertService;
    private final UserService userService;
    private  final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final MethodHelper methodHelper;
    private final AdvertRequest advertRequest;
    private final AdvertMapper advertMapper;


    public List<AdvertResponse> getAuthenticatedUsersFavorites(HttpServletRequest httpServletRequest) {

        // HttpServletRequest kullanarak oturumdaki kullanıcı e-posta adresini al
        String email = (String) httpServletRequest.getAttribute("email");

        //User user = methodHelper.findByUserByEmail(email); cahitin yazdıgı method il

        // Kullanıcıyı e-posta adresine göre veritabanından bul
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_IS_NOT_FOUND));


        // Kullanıcının ID'sini al
        Long userId = user.getId();

        // Kullanıcının favori ilanlarını repositoryden çek:
        List<Favorites> favoriteList = favoritesRepository.findFavorites(userId);

        // Tüm ilanları al:
        //List<Advert> allAdverts = advertService.getAllAdverts(); //advert service de mehmet hoca ile yazdıgımız method. ama bu kullanıslı olmadı bence

        List<Advert> favoriteAdverts = new ArrayList<>();

        for (Favorites favorite : favoriteList
        ) {
            favoriteAdverts.add(favorite.getAdvert());

        }

        return favoriteAdverts
                .stream().
                map(advertMapper::mapAdvertToAdvertResponse).
                collect(Collectors.toList());
    }

    //////////////////


    public List<AdvertResponse> getUsersFavorites(Long id) {

        // Kullanıcı ID'sine göre favori ilanları al
        List<Favorites> favoritesList = (List<Favorites>) methodHelper.findUserWithId(id);

        List<Advert> favoriteAdvert = new ArrayList<>();

        for (Favorites favorite : favoritesList
        ) {
            favoriteAdvert.add(favorite.getAdvert());

        }

        // Favori ilanları AdvertResponse nesnelerine dönüştür ve döndür
        return favoriteAdvert.
                stream().
                map(advertMapper::mapAdvertToAdvertResponse).
                collect(Collectors.toList());

    }


///////////////////


    public ResponseMessage<List<Favorites>> addAndRemoveAuthenticatedUserFavorites(HttpServletRequest
                                                                         httpServletRequest, AdvertRequest advertRequest, Long id) {

        Long userId = MethodHelper.getUserIdFromRequest(httpServletRequest, userRepository);
        Long advertId = advertRequest.getAdvertId(); //id gelmiyor mehmet hocayla konusmalı. getAdvertIdType geliyor

        // Favori ilanın var olup olmadığını kontrol et
        boolean isFavorite = favoritesRepository.existsByUserIdAndAdvertId(userId, advertId);


        if (isFavorite) {
            favoritesRepository.deleteFavoriteIfExists(userId, advertId);

        } else { // Eğer favori yoksa, favori ekle
            favoritesRepository.addFavoriteIfNotExists(userId, advertId);

        }

        List<Favorites> updatedFavorites = favoritesRepository.findFavoritesByUserId(userId);
            AdvertResponse advertResponse = new AdvertResponse();
            advertResponse.setFavoritesList(updatedFavorites); //mehmet hocada setFavori için method ?

        return ResponseMessage.<List<Favorites>>builder()
                .object(updatedFavorites)
                .message(SuccesMessages.FAVORITES_UPDATED_SUCCESFULLY)
                .status(HttpStatus.OK)
                .build();
    }


//////////////////////////////////



    public ResponseMessage removeAllFavoritesofAuthenticatedUser(HttpServletRequest httpServletRequest, Long id) {


        List<Favorites> favoritesList = (List<Favorites>) methodHelper.findUserWithId(id);
        // Favori listesi boş değilse, tüm favorileri sil
         if (!favoritesList.isEmpty()) {
            try {
                favoritesRepository.deleteAll(favoritesList);

            } catch (Exception e) {

            }
        }
        return ResponseMessage.<TourRequestResponse>builder()
                .status(HttpStatus.OK)
                .message(SuccesMessages.TOUR_REQUEST_DELETED_SUCCESSFULLY)
                .build();

    }



    /////////////////////



    public ResponseMessage removeAllFavoritesOfAUser(Long id) {

        try {
            favoritesRepository.deleteAllByUserId(id);
        } catch (Exception e){

        }

        return ResponseMessage.builder()
                .message(SuccesMessages.ALL_FAVORITE_REMOVED_SUCCESSFULLY)
                .build();
    }


    /////////////////////



    public ResponseMessage removeFavoriteByIdForAdmin(Long id) {

        try {
            favoritesRepository.deleteById(id);
        }catch (Exception e){

        }

        return ResponseMessage.builder()
                .message(SuccesMessages.FAVORITE_DELETED_SUCCESSFULLY)
                .build();

    }



}





