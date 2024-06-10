package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.domain.enums.StatusType;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.payload.mappers.TourRequestMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import com.cossinest.homes.repository.business.TourRequestRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.helper.PageableHelper;
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
    private final UserRoleService userRoleService;
    private final PageableHelper pageableHelper;
    private  final AdvertService advertService;




//todo save methodu kontrol edilecek!
    public ResponseMessage<TourRequestResponse> saveTourRequest(TourRequestRequest tourRequestRequest, HttpServletRequest httpServletRequest) {

        // Advertta Date-time cakismasi var mi?
       List<TourRequest> tourRequestsFromRepo = getAlTourRequestl();
       dateTimeValidator.checkConflictTourRequestFromRepoByAdvert(tourRequestsFromRepo,tourRequestRequest);

       //UserGuest için aynı satte requesti var mı?
        String userEmail = (String) httpServletRequest.getAttribute("email");
        User userGuest = methodHelper.findByUserByEmail(userEmail);
        dateTimeValidator.checkConflictTourRequestFromRepoByUserForGuest(userGuest,tourRequestRequest);

        //UserOwner için çakışma kontrolü
        Advert advert = advertService.getAdvertForFaavorites(tourRequestRequest.getAdvertId());
        Long ownerId = advert.getId();
        User ownerUser = methodHelper.findUserWithId(ownerId);
        dateTimeValidator.checkConflictTourRequestFromRepoByUserForOwner(ownerUser,tourRequestRequest);


        TourRequest mappedTourRequest = tourRequestMapper.tourRequestRequestToTourRequest(tourRequestRequest,advert);
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


    //getAll-Response

    public TourRequest findTourRequestById(Long id){
        return tourRequestRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.NOT_FOUND_TOUR_REQUEST));
    }


    public ResponseMessage<Page<TourRequestResponse>> getAllTourRequestByPageAuth(HttpServletRequest httpServletRequest, int page, int size, String sort, String type, String createAt, String tourTime, String status, String tourDate) {

        String userEmail = (String) httpServletRequest.getAttribute("email");
        User userByEmail = methodHelper.findByUserByEmail(userEmail);
               //Role kontrolü
       methodHelper.controlRoles(userByEmail,RoleType.CUSTOMER);

        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);

        Page<TourRequest> tourRequests = tourRequestRepository.findAllByQueryAuth(pageable,userByEmail.getId(),createAt,tourTime,status,tourDate);

        return ResponseMessage.<Page<TourRequestResponse>>builder()
                .object(tourRequests.map(tourRequestMapper::tourRequestToTourRequestResponse))
                .status(HttpStatus.OK)
                .build();
    }
    
        public ResponseEntity<Page<TourRequestResponse>> getAllTourRequestByPageAdmin(
            HttpServletRequest httpServletRequest, int page, int size, String sort, String type,String createAt,String tourTime,String status,String tourDate) {

        String userEmail = (String) httpServletRequest.getAttribute("email");
        User admin = methodHelper.findByUserByEmail(userEmail);

        //Role kontrolü
        methodHelper.controlRoles(admin,RoleType.ADMIN,RoleType.MANAGER);

       Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);
        Page<TourRequest> allTourRequests = tourRequestRepository.findAllByQueryAdmin(pageable,createAt,tourTime,status,tourDate);

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
        Advert advert = advertService.getAdvertForFaavorites(tourRequestRequest.getAdvertId());
        TourRequest updatedTourRequest = tourRequestMapper.tourRequestRequestToTourRequest(tourRequestRequest,advert);
        updatedTourRequest.setId(id);
        updatedTourRequest.setStatus(StatusType.PENDING);
        updatedTourRequest.setGuestUserId(guestUser);
        updatedTourRequest.setOwnerUserId(advert.getUser());



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

    
}
