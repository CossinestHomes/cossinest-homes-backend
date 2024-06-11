package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Advert;

import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.domain.concretes.user.User;

import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.domain.enums.StatusType;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.repository.business.*;
import com.cossinest.homes.repository.user.UserRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.user.UserService;
import com.cossinest.homes.service.validator.DateTimeValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final LogRepository logRepository;
    private final MethodHelper methodHelper;
    private final CategoryRepository categoryRepository;
    private final AdvertRepository advertRepository;
    private final AdvertTypesRepository advertTypesRepository;
    private final TourRequestRepository tourRequestRepository;
    private final UserRepository userRepository;
    private final AdvertService advertService;
    private final UserService userService;
    private final DateTimeValidator dateTimeValidator;
    private final TourRequestService tourRequestService;


    public ResponseEntity<Map<String, Long>> getStaticts(HttpServletRequest request) {
        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.ADMIN, RoleType.MANAGER);
        Map<String, Long> logMap = new HashMap<>();

        Long categoryNo = categoryRepository.count();
        Long advertNo = advertRepository.count();
        Long advertTypeNo = advertTypesRepository.count();
        Long tourRequestNo = tourRequestRepository.count();
        Long userNo = userRepository.count();

        logMap.put("categories", categoryNo);
        logMap.put("adverts", advertNo);
        logMap.put("advertTypes", advertTypeNo);
        logMap.put("tourRequests", tourRequestNo);
        logMap.put("customers", userNo);

        return ResponseEntity.ok(logMap);
    }


    public ResponseEntity<byte[]> getPopulerAdverts(int amount, HttpServletRequest request) {

        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.MANAGER, RoleType.ADMIN);
        Pageable pageable = PageRequest.of(0, amount);

        Page<Advert> adverts = advertService.getPopulerAdverts(pageable);
        return methodHelper.excelResponse(adverts);


    }

    public ResponseEntity<byte[]> getUsersWithRol(String rol, HttpServletRequest request) {

        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.MANAGER, RoleType.ADMIN);

        RoleType roleType;
        try {
            roleType = RoleType.valueOf(rol);

        } catch (BadRequestException e) {
            throw new BadRequestException(ErrorMessages.ADVERT_STATUS_NOT_FOUND);
        }

        List<User> users = userService.getUsersByRoleType(roleType);

        return methodHelper.excelResponse(users);


    }

    public ResponseEntity<byte[]> getTourRequest(HttpServletRequest request, String date1, String date2, String status) {

        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.ADMIN, RoleType.MANAGER);

        LocalDate begin = LocalDate.parse(date1);
        LocalDate end = LocalDate.parse(date2);
        dateTimeValidator.checkBeginTimeAndEndTime(begin, end);

        StatusType statusType;
        try {
            statusType = StatusType.valueOf(status);

        } catch (BadRequestException e) {
            throw new BadRequestException(ErrorMessages.ADVERT_STATUS_NOT_FOUND);
        }
        List<TourRequest> tourRequests = tourRequestService.getTourRequest(date1, date2, statusType);

        return methodHelper.excelResponse(tourRequests);


    }

    public ResponseEntity<byte[]> getAdverts(HttpServletRequest request, String date1, String date2, String category, String type, String status) {

        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.ADMIN, RoleType.MANAGER);

        List<Advert> adverts = advertService.getAdvertsReport(date1, date2, category, type.toLowerCase(), status);

        return methodHelper.excelResponse(adverts);


    }
}
