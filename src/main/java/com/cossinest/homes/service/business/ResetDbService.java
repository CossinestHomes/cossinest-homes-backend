package com.cossinest.homes.service.business;

import com.cossinest.homes.contactMessage.service.ContactMessageService;
import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.AdvertType;
import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.repository.business.*;
import com.cossinest.homes.repository.user.UserRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.user.UserService;
import com.cossinest.homes.service.validator.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResetDbService {


    /*private final UserService userService;
    private final TourRequestService tourRequestService;
    private final AdvertService advertService;
    private final FavoritesService favoritesService;
    private final AdvertTypesService advertTypesService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final CountryService countryService;
    private final DistrictService districtService;
    private final ReportService reportService;
    private final ContactMessageService contactMessageService;
    private final CategoryPropertyKeyService categoryPropertyKeyService;
    private final CategoryPropertyValueService categoryPropertyValueService;
    private final UserRoleService userRoleService;
    private final ImagesService imagesService;
    private final LogService logService;
    private final MethodHelper methodHelper;*/

   /* public void resetDb(HttpServletRequest request) {
       User user =methodHelper.getUserByHttpRequest(request);
       methodHelper.checkRoles(user,RoleType.ADMIN);

       *//* userService.resetUserTables();
        tourRequestService.resetTourRequestTables();
        advertService.resetAdvertTables();
        favoritesService.resetFavoritesTables();
        advertTypesService.resetAdvertTypesTables();
        categoryService.resetCategoryTables();
        cityService.resetCityTables();
        countryService.resetCountryTables();
        districtService.resetDistrictTables();
       // reportService.resetReportTables();
        logService.resetLogTables();
        contactMessageService.resetContactMessageTables();
        categoryPropertyKeyService.resetCategoryPropertyKeyTables();
        categoryPropertyValueService.resetCategoryPropertyValueTables();
        userRoleService.resetUserRoleTables();
        imagesService.resetImageTables();*//*


    }*/

    private final AdvertRepository advertRepository;
    private final ImagesRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final AdvertTypesRepository advertTypesRepository;
    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private final CategoryPropertyValueRepository categoryPropertyValueRepository;
    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final TourRequestRepository tourRequestRepository;


    @Transactional
    public ResponseEntity<String> resetDatabase() {

        categoryPropertyValueRepository.deleteAll();
        favoritesRepository.deleteAll();
        imageRepository.deleteAll();
        tourRequestRepository.deleteAll();


        List<Advert> alladverts= advertRepository.findAll();
        for(Advert advert : alladverts){
            if(!advert.getBuiltIn()){
                advertRepository.delete(advert);
            }
        }

        List<Category> allCategory=categoryRepository.findAll();
        for (Category category:allCategory){
            if (!category.getBuiltIn()){
                categoryRepository.delete(category);
            }
        }

        List<AdvertType> allAdvertTypes=advertTypesRepository.findAll();
        for (AdvertType advertType: allAdvertTypes){
            if (advertType.getBuiltIn() == null || !advertType.getBuiltIn()){
                advertTypesRepository.delete(advertType);
            }
        }

        List<CategoryPropertyKey> allCategoryPropertyKey =categoryPropertyKeyRepository.findAll();
        for (CategoryPropertyKey categoryPropertyKey:allCategoryPropertyKey){
            if (!categoryPropertyKey.getBuiltIn()){
                categoryPropertyKeyRepository.delete(categoryPropertyKey);
            }
        }

        List<User> allUser=userRepository.findAll();
        for (User user:allUser){
            if (!user.getBuiltIn()){
                userRepository.delete(user);
            }
        }

        return ResponseEntity.ok("Reset DB successfully");

    }
}
