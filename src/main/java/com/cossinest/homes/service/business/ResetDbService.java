package com.cossinest.homes.service.business;

import com.cossinest.homes.contactMessage.service.ContactMessageService;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.user.UserService;
import com.cossinest.homes.service.validator.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetDbService {


    private final UserService userService;
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
    private final MethodHelper methodHelper;

    public void resetDb(HttpServletRequest request) {
       User user =methodHelper.getUserByHttpRequest(request);
       methodHelper.checkRoles(user,RoleType.ADMIN);

        userService.resetUserTables();
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
        imagesService.resetImageTables();


    }
}
