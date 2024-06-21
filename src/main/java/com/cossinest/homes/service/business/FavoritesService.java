package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.Favorites;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.AdvertMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.AdvertRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import com.cossinest.homes.repository.business.FavoritesRepository;
import com.cossinest.homes.repository.user.UserRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final AdvertService advertService;
    private final UserService userService;
    private final FavoritesRepository favoritesRepository;
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

        return favoriteAdverts.stream().map(advertMapper::mapAdvertToAdvertResponse).collect(Collectors.toList());

    }

    public List<AdvertResponse> getUsersFavorites(Long id) {

        // Kullanıcı ID'sine göre favori ilanları al
        List<Favorites> favoritesList = (List<Favorites>) methodHelper.findUserWithId(id);

        //TODO Burası User döner
           /* User user = methodHelper.findUserWithId(id);
            List<Advert> favorites = user.getFavoritesList().stream().map(Favorites::getAdvert).toList();
            return favorites.stream().map(advertMapper::mapAdvertToAdvertResponse).collect(Collectors.toList()); */

        List<Advert> favoriteAdvert = new ArrayList<>();

        for (Favorites favorite : favoritesList
        ) {
            favoriteAdvert.add(favorite.getAdvert());

        }

        // Favori ilanları AdvertResponse nesnelerine dönüştür ve döndür
        return favoriteAdvert.stream().map(advertMapper::mapAdvertToAdvertResponse).collect(Collectors.toList());

    }



        public AdvertResponse addAndRemoveAuthenticatedUserFavorites(HttpServletRequest httpServletRequest,Long id) {

            //TODO Repo göndermeye gerek yok, zaten orda da var
            User user = methodHelper.getUserByHttpRequest(httpServletRequest);
            
            //TODO property olarak gelen id zaten direkt Advert id, onu kullanmalıyız
            Advert advert = advertService.getAdvertForFaavorites(id);
          Favorites favoriteByAdvert = favoritesRepository.isExistsFavByAdvert(advert.getId(),user.getId());
            if(favoriteByAdvert!=null) {
            user.getFavoritesList().remove(favoriteByAdvert);
            }else {
                user.getFavoritesList().add(favoriteByAdvert);
            }

            return advertMapper.mapAdvertToAdvertResponse(advert);

        }







    public ResponseMessage removeAllFavoritesOfAUser(HttpServletRequest httpServletRequest) {

        Long userId = MethodHelper.getUserIdFromRequest(httpServletRequest, userRepository);

        favoritesRepository.deleteAllByUserId(userId);
return null;
    }

    public ResponseMessage removeAllFavoritesofAuthenticatedUser(HttpServletRequest httpServletRequest) {
        User user = methodHelper.getUserByHttpRequest(httpServletRequest);
        methodHelper.controlRoles(user, RoleType.CUSTOMER);

     if(user.getFavoritesList()!=null){

         user.getFavoritesList().clear();

         userRepository.save(user);
     }
     return ResponseMessage.<T>builder()
             .status(HttpStatus.OK)
             .message(SuccesMessages.FAVORITE_REMOVED_SUCCESSFULLY)
             .build();
    }

    public void resetFavoritesTables() {
        favoritesRepository.deleteAll();
    }
}



