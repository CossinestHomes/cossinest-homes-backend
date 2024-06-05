package com.cossinest.homes.service.business;


import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.domain.enums.StatusType;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.payload.mappers.TourRequestMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import com.cossinest.homes.repository.business.TourRequestRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.user.UserService;
import com.cossinest.homes.service.validator.DateTimeValidator;
import com.cossinest.homes.service.validator.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TourRequestService {


    private final TourRequestRepository tourRequestRepository;
    private final TourRequestMapper tourRequestMapper;
    private final DateTimeValidator dateTimeValidator;
    private final MethodHelper methodHelper;


    public ResponseMessage<TourRequestResponse> saveTourRequest(TourRequestRequest tourRequestRequest, HttpServletRequest httpServletRequest) {

        // Advertta Date-time cakismasi var mi?
       List<TourRequest> tourRequestsFromRepo = getAlTourRequestl();
       dateTimeValidator.checkConflictTourRequestFromRepoByAdvert(tourRequestsFromRepo,tourRequestRequest);

       //UserGuest için aynı satte requesti var mı?
        String userEmail = (String) httpServletRequest.getAttribute("email");
        User userGuest = methodHelper.findByUserByEmail(userEmail);
        dateTimeValidator.checkConflictTourRequestFromRepoByUserForGuest(userGuest,tourRequestRequest);

        //UserOwner için çakışma kontrolü
        Long ownerId = tourRequestRequest.getAdvertId().getUser().getId();
        User ownerUser = methodHelper.findUserWithId(ownerId);
        dateTimeValidator.checkConflictTourRequestFromRepoByUserForOwner(ownerUser,tourRequestRequest);

        TourRequest mappedTourRequest = tourRequestMapper.tourRequestRequestToTourRequest(tourRequestRequest);
        mappedTourRequest.setStatus(StatusType.PENDING);
        mappedTourRequest.setOwnerUserId(ownerUser);
        mappedTourRequest.setGuestUserId(userGuest);

        TourRequest savedTourRequest = tourRequestRepository.save(mappedTourRequest);

        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.tourRequestToTourRequestResponse(savedTourRequest))
                .status(HttpStatus.CREATED)
                .message(SuccesMessages.TOUR_REQUEST_CREATED_SUCCESSFULLY)
                .build();
    }

    public List<TourRequest> getAlTourRequestl(){

        return tourRequestRepository.findAll();
    }


    public ResponseMessage<Page<TourRequestResponse>> getAllTourRequestByPageAuth(HttpServletRequest httpServletRequest, int page, int size, String sort, String type, String createAt, String tourTime, String status, String tourDate) {

        String userEmail = (String) httpServletRequest.getAttribute("email");
        User userByEmail = methodHelper.findByUserByEmail(userEmail);

        //Role kontrolü
        Set<RoleType> roles=new HashSet<>();
        roles.add(RoleType.CUSTOMER);

        for (UserRole role:userByEmail.getUserRole()) {
            if(!(roles.contains(role))){
                throw new BadRequestException(ErrorMessages.NOT_HAVE_AUTHORITY);
            }
        }


        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
        if (type.equals("desc")){
            pageable = PageRequest.of(page,size, Sort.by(sort).descending());
        }

        Page<TourRequest> tourRequests = tourRequestRepository.findAllByQuery(pageable,createAt,tourTime,status,tourDate);

        return ResponseMessage.<Page<TourRequestResponse>>builder()
                .object(tourRequests.map(tourRequestMapper::tourRequestToTourRequestResponse))
                .status(HttpStatus.OK)
                .build();
    }

    public ResponseEntity<TourRequestResponse> getTourRequestByIdAuth(Long id, HttpServletRequest httpServletRequest) {



    }
}
