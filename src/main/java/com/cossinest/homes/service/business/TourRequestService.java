package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.LogEnum;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.domain.enums.StatusType;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.TourRequestMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import com.cossinest.homes.repository.business.TourRequestRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.helper.PageableHelper;
import com.cossinest.homes.service.validator.DateTimeValidator;
import com.cossinest.homes.service.validator.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class TourRequestService {


    private final TourRequestRepository tourRequestRepository;
    private final TourRequestMapper tourRequestMapper;
    private final DateTimeValidator dateTimeValidator;
    private final MethodHelper methodHelper;
    private final UserRoleService userRoleService;
    private final PageableHelper pageableHelper;
    private  final AdvertService advertService;
    private final LogService logService;




    public ResponseMessage<TourRequestResponse>saveTourRequest(TourRequestRequest tourRequestRequest, HttpServletRequest httpServletRequest) {

        // Advertta Date-time cakismasi var mi?
       List<TourRequest> tourRequestsFromRepo = getAlTourRequestl();
       dateTimeValidator.checkConflictTourRequestFromRepoByAdvert(tourRequestsFromRepo,tourRequestRequest);

       //UserGuest için aynı satte requesti var mı?
        String userEmail = (String) httpServletRequest.getAttribute("email");
        User userGuest = methodHelper.findByUserByEmail(userEmail);
        dateTimeValidator.checkConflictTourRequestFromRepoByUserForGuest(userGuest,tourRequestRequest);

        //UserOwner için çakışma kontrolü //TODO burayi anlayamadim. id zaten var ve advert id neden ownerId oldu
        Advert advert = advertService.getAdvertForFavorites(tourRequestRequest.getAdvertId());
        User ownerUser = advert.getUser();
       // User ownerUser = methodHelper.findUserWithId(ownerId);
        dateTimeValidator.checkConflictTourRequestFromRepoByUserForOwner(ownerUser,tourRequestRequest);

        if (userGuest.getId().equals(ownerUser.getId())) {
            throw new  BadRequestException("Can not book your own advert");
        }

        TourRequest mappedTourRequest = tourRequestMapper.tourRequestRequestToTourRequest(tourRequestRequest,advert);
        mappedTourRequest.setStatus(StatusType.PENDING);
        mappedTourRequest.setOwnerUserId(ownerUser);
        mappedTourRequest.setGuestUserId(userGuest);

        TourRequest savedTourRequest = tourRequestRepository.save(mappedTourRequest);

        logService.createLogEvent(userGuest,savedTourRequest.getAdvertId(), LogEnum.TOUR_REQUEST_CREATED);


        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.tourRequestToTourRequestResponse(savedTourRequest))
                .status(HttpStatus.CREATED)
                .message(SuccesMessages.TOUR_REQUEST_CREATED_SUCCESSFULLY)
                .build();
    }

    public List<TourRequest> getAlTourRequestl(){

        return tourRequestRepository.findAll();
    }


    //getAll-Response

    public TourRequest findTourRequestById(Long id){
        return tourRequestRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.NOT_FOUND_TOUR_REQUEST));
    }


//    public ResponseMessage<Page<TourRequestResponse>> getAllTourRequestByPageAuth(
//            HttpServletRequest httpServletRequest,
//            int page,
//            int size,
//            String sort,
//            String type,
//            String createAt,
//            String tourTime,
//            String status,
//            String tourDate) {
//
//        String userEmail = (String) httpServletRequest.getAttribute("email");
//        User userByEmail = methodHelper.findByUserByEmail(userEmail);
//
//        // Role kontrolü
//        methodHelper.checkRoles(userByEmail, RoleType.CUSTOMER, RoleType.ADMIN);
//
//        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
//
//        // Parametreleri uygun veri tiplerine dönüştür
//      //  LocalDateTime createAtDateTime = (createAt != null && !createAt.isEmpty()) ? LocalDateTime.parse(createAt) : null;
//       // LocalTime tourTimeParsed = (tourTime != null && !tourTime.isEmpty()) ? LocalTime.parse(tourTime) : null;
//       // StatusType statusType = (status != null && !status.isEmpty()) ? StatusType.valueOf(status) : null;
//       // LocalDate tourDateParsed = (tourDate != null && !tourDate.isEmpty()) ? LocalDate.parse(tourDate) : null;
//
//        Page<TourRequest> tourRequests = tourRequestRepository.findAllByQueryAuth(
//                pageable,
//                userByEmail.getId(), // userId parametresi burada Long türündedir
//                createAt,
//                tourTime,
//                status,
//                tourDate
//        );
//
//        Page<TourRequestResponse> tourRequestResponses = tourRequests.map(tourRequestMapper::tourRequestToTourRequestResponse);
//
//        return ResponseMessage.<Page<TourRequestResponse>>builder()
//                .object(tourRequestResponses)
//                .status(HttpStatus.OK)
//                .build();}

    public Page<TourRequestResponse> getAllTourRequestByPageForCustomer(HttpServletRequest httpServletRequest, String query, int page, int size, String sort, String type) {
        String userEmail = (String) httpServletRequest.getAttribute("email");
        User userByEmail = methodHelper.findByUserByEmail(userEmail);
        methodHelper.checkRoles(userByEmail, RoleType.CUSTOMER, RoleType.ADMIN);

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        if (query != null && !query.isEmpty()) {
            return tourRequestRepository.findAllByGuestUserId_IdAndQuery(userByEmail.getId(), query, pageable)
                    .map(tourRequestMapper::tourRequestToTourRequestResponse);
        }else {

        return tourRequestRepository.findAllByGuestUserId_Id(userByEmail.getId(), pageable)
                .map(tourRequestMapper::tourRequestToTourRequestResponse);
    }
    }


        public ResponseEntity<Page<TourRequestResponse>> getAllTourRequestByPageAdmin(
            HttpServletRequest httpServletRequest, int page, int size, String sort, String type,String createAt,String tourTime,String status,String tourDate) {

            String userEmail = (String) httpServletRequest.getAttribute("email");
            User admin = methodHelper.findByUserByEmail(userEmail);

            // Role kontrolü
            methodHelper.controlRoles(admin, RoleType.ADMIN, RoleType.MANAGER);

            LocalDateTime createAtDate = (createAt != null && !createAt.isEmpty()) ? LocalDateTime.parse(createAt) : null;
            LocalTime tourTimeParsed = (tourTime != null && !tourTime.isEmpty()) ? LocalTime.parse(tourTime) : null;
            String statusType = (status != null && !status.isEmpty()) ? status : null;
            LocalDate tourDateParsed = (tourDate != null && !tourDate.isEmpty()) ? LocalDate.parse(tourDate) : null;
            StatusType statusType1 = null;

            for (StatusType statusType2 :StatusType.values()  ) {
                if(statusType2.name.equalsIgnoreCase(status)){
                    statusType1 = statusType2;
                }
            }

            Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
            Page<TourRequest> allTourRequests = tourRequestRepository.findAllByQueryAdmin(pageable, createAtDate, tourTimeParsed, statusType1, tourDateParsed);

            return ResponseEntity.ok(allTourRequests.map(tourRequestMapper::tourRequestToTourRequestResponse));
    }








    public ResponseEntity<TourRequestResponse> getTourRequestByIdAuth(Long id, HttpServletRequest httpServletRequest) {
        //Rol kontrolü
        User guestUser =methodHelper.getUserByHttpRequest(httpServletRequest);
        methodHelper.controlRoles(guestUser,RoleType.CUSTOMER);


       TourRequest tourRequest = tourRequestRepository.findByIdByCustomer(guestUser.getId(),id);

       return ResponseEntity.ok(tourRequestMapper.tourRequestToTourRequestResponse(tourRequest));
    }

    public ResponseMessage<TourRequestResponse> getTourRequestByIdAdmin(Long id,HttpServletRequest httpServletRequest){

        //Rol kontrolü
        User guestUser =methodHelper.getUserByHttpRequest(httpServletRequest);
        methodHelper.controlRoles(guestUser,RoleType.ADMIN,RoleType.MANAGER);

        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.tourRequestToTourRequestResponse(findTourRequestById(id)))
                .status(HttpStatus.OK)
                .build();
    }


    public ResponseMessage<TourRequestResponse> updateTourRequestAuth(TourRequestRequest tourRequestRequest, Long id, HttpServletRequest httpServletRequest) {

        //Rol kontrolü
        User guestUser =methodHelper.getUserByHttpRequest(httpServletRequest);
        methodHelper.controlRoles(guestUser,RoleType.CUSTOMER);

        //id ile tour request var mi?
        TourRequest tourRequest = findTourRequestById(id);

        //Status pending or rejected ise yapılabilir  SADECE APPROVED OLDUĞUNDA ERROR FIRLATTIM
        if(tourRequest.getStatus().getName().equals("1")){
            throw new BadRequestException(ErrorMessages.TOUR_REQUEST_CAN_NOT_BE_CHANGED);
        }

        //cakismalar
        List<TourRequest> tourRequestList = getAlTourRequestl();

        dateTimeValidator.checkConflictTourRequestFromRepoByAdvert(tourRequestList,tourRequestRequest);
        dateTimeValidator.checkConflictTourRequestFromRepoByUserForOwner(tourRequest.getOwnerUserId(),tourRequestRequest);
        dateTimeValidator.checkConflictTourRequestFromRepoByUserForGuest(guestUser,tourRequestRequest);

        //request to entity
        Advert advert = advertService.getAdvertForFavorites(tourRequestRequest.getAdvertId());
        TourRequest updatedTourRequest = tourRequestMapper.tourRequestRequestToTourRequest(tourRequestRequest,advert);
        updatedTourRequest.setId(id);
        updatedTourRequest.setStatus(StatusType.PENDING);
        updatedTourRequest.setGuestUserId(guestUser);
        updatedTourRequest.setOwnerUserId(advert.getUser());
        updatedTourRequest.setCreateAt(LocalDateTime.now());


        logService.createLogEvent(guestUser,advert, LogEnum.TOUR_REQUEST_ACCEPTED);

        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.tourRequestToTourRequestResponse( tourRequestRepository.save(updatedTourRequest)))
                .status(HttpStatus.OK)
                .message(String.format(SuccesMessages.TOUR_REQUEST_UPDATED_SUCCESSFULLY,id))
                .build();
    }

    public ResponseMessage<TourRequestResponse> updateTourRequestCancel(Long id, HttpServletRequest httpServletRequest) {

    //Guest isteğini iptal ediyor

        //Rol kontrolü
        User guestUser =methodHelper.getUserByHttpRequest(httpServletRequest);
        methodHelper.controlRoles(guestUser,RoleType.CUSTOMER);

        //id ile tour request var mi?
        TourRequest tourRequest =tourRequestRepository.findByIdByCustomer(guestUser.getId(),id);

         tourRequest.setStatus(StatusType.CANCELED);

        logService.createLogEvent(guestUser,tourRequest.getAdvertId(), LogEnum.TOUR_REQUEST_CANCELED);
        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.tourRequestToTourRequestResponse(tourRequestRepository.save(tourRequest)))
                .status(HttpStatus.OK)
                .message(SuccesMessages.TOUR_REQUEST_CANCELED_SUCCESSFULLY)
                .build();
    }

    public ResponseMessage<TourRequestResponse> updateTourRequestApprove(Long id, HttpServletRequest httpServletRequest) {

        //Owner talebi kabul ediyor
        //Rol kontrolü
        User guestUser =methodHelper.getUserByHttpRequest(httpServletRequest);
        methodHelper.controlRoles(guestUser,RoleType.CUSTOMER);

        //id ile tour request var mi?
        TourRequest tourRequest =tourRequestRepository.findByIdByCustomer(guestUser.getId(),id);

        tourRequest.setStatus(StatusType.APPROVED);

        logService.createLogEvent(guestUser,tourRequest.getAdvertId(), LogEnum.TOUR_REQUEST_ACCEPTED);

        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.tourRequestToTourRequestResponse(tourRequestRepository.save(tourRequest)))
                .status(HttpStatus.OK)
                .message(SuccesMessages.TOUR_REQUEST_APPROVED_SUCCESSFULLY)
                .build();


    }

    public ResponseMessage<TourRequestResponse> updateTourRequestDecline(Long id, HttpServletRequest httpServletRequest) {

        //Owner talebi red ediyor

        //Rol kontrolü
        User guestUser =methodHelper.getUserByHttpRequest(httpServletRequest);
        methodHelper.controlRoles(guestUser,RoleType.CUSTOMER);

        //id ile tour request var mi?
        TourRequest tourRequest =tourRequestRepository.findByIdByCustomer(guestUser.getId(),id);

        tourRequest.setStatus(StatusType.DECLINED);

        logService.createLogEvent(guestUser,tourRequest.getAdvertId(), LogEnum.TOUR_REQUEST_DECLINED);

        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.tourRequestToTourRequestResponse(tourRequestRepository.save(tourRequest)))
                .status(HttpStatus.OK)
                .message(SuccesMessages.TOUR_REQUEST_DECLINED_SUCCESSFULLY)
                .build();


    }

    public ResponseMessage<TourRequestResponse> deleteTourRequest(Long id, HttpServletRequest httpServletRequest) {

        //Talep yönetici tarafından siliniyor

        //Rol kontrolü
        User guestUser =methodHelper.getUserByHttpRequest(httpServletRequest);
        methodHelper.controlRoles(guestUser,RoleType.ADMIN,RoleType.MANAGER);

        //id ile tour request var mi?
        TourRequest tourRequest =findTourRequestById(id);

        tourRequestRepository.delete(tourRequest);



        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.tourRequestToTourRequestResponse(tourRequest))
                .status(HttpStatus.OK)
                .message(SuccesMessages.TOUR_REQUEST_DELETED_SUCCESSFULLY)
                .build();

    }



    public List<TourRequest> getTourRequest(LocalDateTime date1, LocalDateTime date2, StatusType statusType) {

       return tourRequestRepository.getTourRequest(date1,date2,statusType);

    }

    public void resetTourRequestTables() {
        tourRequestRepository.deleteAll();
    }

    public Set<TourRequest> getTourRequestsById(Set<Long> tourRequestIdList) {

      return   tourRequestRepository.findByIdIn(tourRequestIdList);

    }


}
