package com.cossinest.homes.service.business;


import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.payload.mappers.TourRequestMapper;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import com.cossinest.homes.repository.business.TourRequestRepository;
import com.cossinest.homes.service.validator.DateTimeValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class TourRequestService {


    private final TourRequestRepository tourRequestRepository;
    private final TourRequestMapper tourRequestMapper;
    private final DateTimeValidator dateTimeValidator;
    private final UserService userService;

    public ResponseMessage<TourRequestResponse> saveTourRequest(TourRequestRequest tourRequestRequest, HttpServletRequest httpServletRequest) {

        // Advertta Date-time cakismasi var mi?
       List<TourRequest> tourRequestsFromRepo = getAlTourRequestl();
       dateTimeValidator.checkConflictTourRequestFromRepoByAdvert(tourRequestsFromRepo,tourRequestRequest);

       //Kullanıcının aynı satte baska requesti var mı?
        String userEmail = (String) httpServletRequest.getAttribute("email");
        User userGuest = userService.findByEmail(userEmail);

        dateTimeValidator.checkConflictTourRequestFromRepoByUser(userGuest,tourRequestRequest);



    }

    public List<TourRequest> getAlTourRequestl(){

        return tourRequestRepository.findAll();
    }
}
