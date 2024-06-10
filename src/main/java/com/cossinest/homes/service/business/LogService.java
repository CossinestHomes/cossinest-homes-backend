package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Log;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.payload.response.business.LogStaticResponse;
import com.cossinest.homes.repository.business.LogRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final MethodHelper methodHelper;
    private final CategoryService categoryService;
    private final AdvertService advertService;
    private final AdvertTypesService advertTypesService;
    private final TourRequestService tourRequestService;
    private final UserService userService;


    public ResponseEntity<Map<String,Long>> getStaticts(HttpServletRequest request) {
      User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.ADMIN,RoleType.MANAGER);
        Log log=new Log();
        Long catNo =categoryService.getCategoriesNumber;
        Long advertNo = categoryService.getAdvertNumber;
        Long advertTypeNo = categoryService.getAdvertTypesNumber;
        Long tourRequestNo = categoryService.getTourRequestNumber;
        Long userNo = categoryService.getUserNumber;


    }
}
