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
import com.cossinest.homes.repository.business.FavoritesRepository;
import com.cossinest.homes.repository.user.UserRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
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
    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final MethodHelper methodHelper;
    private final AdvertMapper advertMapper;


    @Transactional
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

    @Transactional
    public List<AdvertResponse> getUsersFavorites (Long id){

        //TODO user döner
        User user =  methodHelper.findUserWithId(id);
        List<Favorites> userFavorites = favoritesRepository.findFavoritesByUserId(user.getId());

        List<Advert> favoriteAdvert = new ArrayList<>();

        for (Favorites favorite :userFavorites
        ) {
            favoriteAdvert.add(favorite.getAdvert());

        }

        // Favori ilanları AdvertResponse nesnelerine dönüştür ve döndür
        return favoriteAdvert.stream().map(advertMapper::mapAdvertToAdvertResponse).collect(Collectors.toList());

    }

//BAKILACAK!!!! - DELETE KISMI(104)

    @Transactional
    public AdvertResponse addAndRemoveAuthenticatedUserFavorites(HttpServletRequest httpServletRequest, Long advertId)
    {
        User user = methodHelper.getUserByHttpRequest(httpServletRequest);
        Advert advert = advertService.getAdvertForFavorites(advertId);

        // Favori ilanın var olup olmadığını kontrol et
        boolean isFavorite = favoritesRepository.existsByUserIdAndAdvertId(user.getId(),advertId);

        if (isFavorite) {
            Favorites favorite = favoritesRepository.getFavoriteByAdvertAndUser(user.getId(),advertId);
            favoritesRepository.delete(favorite);
            user.setFavoritesList(user.getFavoritesList().stream().filter(t->!t.getId().equals(favorite.getId())).toList());

            //TODO direkt sql ile silinmemeli
            //  favoritesRepository.deleteFavoriteIfExists(user.getId(), advertId);

        } else {
            Favorites favorites = new Favorites();
            favorites.setUser(user);
            favorites.setAdvert(advert);
            favoritesRepository.save(favorites);

        }

        return advertMapper.mapAdvertToAdvertResponse(advert);

    }


    public ResponseMessage removeAllFavoritesofAuthenticatedUser(HttpServletRequest httpServletRequest) {

        User user = methodHelper.getUserByHttpRequest(httpServletRequest);

        List<Favorites> favorites = user.getFavoritesList();

        if (favorites==null){
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_FAVORITES);
        }else {
            favoritesRepository.deleteAll(favorites);
            user.setFavoritesList(new ArrayList<>());
            userRepository.save(user);
        }
        return ResponseMessage.builder()
                .status(HttpStatus.OK)
                .message(SuccesMessages.ALL_FAVORITES_DELETED)
                .build();
    }


    public ResponseMessage removeAllFavoritesOfAUser(HttpServletRequest request,Long userId) {

        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.MANAGER,RoleType.ADMIN);

        User userCustomer = methodHelper.findUserWithId(userId);

        List<Favorites> favorites = userCustomer.getFavoritesList();

        if (favorites==null){
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_FAVORITES);
        }else {
            favoritesRepository.deleteAll(favorites);
            userCustomer.setFavoritesList(new ArrayList<>());
            userRepository.save(userCustomer);
        }
        return ResponseMessage.builder()
                .status(HttpStatus.OK)
                .message(SuccesMessages.ALL_FAVORITES_DELETED)
                .build();
    }

    public ResponseMessage removeFavoriteByIdForAdmin(HttpServletRequest request, Long id) {

        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.MANAGER,RoleType.ADMIN);

        Favorites favorite = favoritesRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.NOT_FOUND_FAVORITE));

        favoritesRepository.delete(favorite);

        return ResponseMessage.builder()
                .status(HttpStatus.OK)
                .message(SuccesMessages.FAVORITE_REMOVED_SUCCESSFULLY)
                .build();

    }

    public void resetFavoritesTables() {

        favoritesRepository.deleteAll();
    }

    public List<Favorites> getFavoritesById(List<Long> favoritesList) {

      return   favoritesRepository.findAllById(favoritesList);

    }

    //yardımcı metod:

    public Integer favoritesCount(List<Favorites> favorites , Long advertId){
        List<Favorites> favoritesOfAdvert= favoritesRepository.findAllByAdvert_Id(advertId);

        if(!favoritesOfAdvert.isEmpty()){
            Integer favoritesCount= favoritesOfAdvert.size();
            return favoritesCount;
        }
        return 0;

    }
}




