package com.cossinest.homes.controller.business;

import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import com.cossinest.homes.service.business.TourRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour-requests") // http://localhost:8080/tour-requests
@RequiredArgsConstructor
public class TourRequestController {

    private final TourRequestService tourRequestService;

    @PostMapping // http://localhost:8080/tour-requests  + POST + JSON
    @PreAuthorize("hasAnyAuthority('CUSTOMER','MANAGER','ADMIN')")
    public ResponseMessage<TourRequestResponse> save(@Valid @RequestBody TourRequestRequest tourRequestRequest, HttpServletRequest httpServletRequest){

        return tourRequestService.saveTourRequest(tourRequestRequest,httpServletRequest);
    }

//    @GetMapping("/auth") // http://localhost:8080/tour-requests/auth?page=0&size=7&sort=tourDate&type=asc  + GET
//    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
//    public ResponseMessage<Page<TourRequestResponse>> getAllTourRequestByPageAuth(
//            HttpServletRequest httpServletRequest,
//            @RequestParam(value = "page",defaultValue = "0") int page,
//            @RequestParam(value = "size",defaultValue = "10") int size,
//            @RequestParam(value = "sort",defaultValue = "tourDate") String sort,
//            @RequestParam(value = "type",defaultValue = "asc") String type,
//            @RequestParam(value = "createAt",required = false) String createAt,
//            @RequestParam(value = "tourTime",required = false) String tourTime,
//            @RequestParam(value = "status",required = false) String status,
//            @RequestParam(value = "tourDate",required = false) String tourDate){
//
//        return tourRequestService.getAllTourRequestByPageAuth(httpServletRequest,page, size,sort,type,createAt,tourTime,status,tourDate);
//    }


    @GetMapping("/customer")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public Page<TourRequestResponse> getAllTourRequestByPageForCustomer(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sort",defaultValue = "tourDate") String sort,
            @RequestParam(value = "type",defaultValue = "asc") String type
    ){
        return tourRequestService.getAllTourRequestByPageForCustomer(httpServletRequest,query,page,size,sort,type);
    }


//    @GetMapping("/admin") // http://localhost:8080/tour-requests/admin?page=0&size=7&sort=createAt&type=asc + GET
//    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
//    public ResponseEntity<Page<TourRequestResponse>> getAllTourRequestByPageAdmin(
//            HttpServletRequest httpServletRequest,
//            @RequestParam(value = "page",defaultValue = "0") int page,
//            @RequestParam(value = "size",defaultValue = "10") int size,
//            @RequestParam(value = "sort",defaultValue = "tourDate") String sort,
//            @RequestParam(value = "type",defaultValue = "asc") String type,
//            @RequestParam(value = "createAt",required = false) String createAt,
//            @RequestParam(value = "tourTime",required = false) String tourTime,
//            @RequestParam(value = "status",required = false) String status,
//            @RequestParam(value = "tourDate",required = false) String tourDate)
//    {
//        return tourRequestService.getAllTourRequestByPageAdmin(httpServletRequest,page, size,sort,type,createAt,tourTime,status,tourDate);
//    }


    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public Page<TourRequestResponse> getAllTourRequestByManagerAndAdminAsPage(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size,
            @RequestParam(value = "sort", defaultValue = "advert_id", required = false) String sort,
            @RequestParam(value = "type", defaultValue = "asc", required = false) String type,
            @RequestParam(value = "query", required = false) String query
    ) {
        return tourRequestService.getAllTourRequestByManagerAndAdminAsPage(page, size, sort, type, query);
    }

    @GetMapping("/{id}/auth") // http://localhost:8080/tour-requests/3/auth + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<TourRequestResponse> getTourRequestByIdAuth(@PathVariable Long id,
                                                                      HttpServletRequest httpServletRequest){
        return tourRequestService.getTourRequestByIdAuth(id,httpServletRequest);

    }

    @GetMapping("/{id}/admin") // http://localhost:8080/tour-requests/2/admin + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<TourRequestResponse> getTourRequestByIdAdmin(@PathVariable Long id,
                                                                       HttpServletRequest httpServletRequest){
        return tourRequestService.getTourRequestByIdAdmin(id,httpServletRequest);
    }

    @PutMapping("/{id}/auth") // http://localhost:8080/tour-requests/2/auth + PUT + JSON
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<TourRequestResponse> updateTourRequestAuth(@Valid @RequestBody TourRequestRequest tourRequestRequest,
                                                                      @PathVariable("id") Long id,
                                                                      HttpServletRequest httpServletRequest){

        // status must be pending
        return tourRequestService.updateTourRequestAuth(tourRequestRequest,id,httpServletRequest);
    }

    @PatchMapping("/{id}/cancel") // http://localhost:8080/tour-requests/2/cancel + PATCH + JSON
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<TourRequestResponse> updateTourRequestCancel(@PathVariable("id") Long id,
                                                                      HttpServletRequest httpServletRequest){

        // status must be pending
        return tourRequestService.updateTourRequestCancel(id,httpServletRequest);
    }

    @PatchMapping("/{id}/approve") // http://localhost:8080/tour-requests/2/approve + PATCH + JSON
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<TourRequestResponse> updateTourRequestApprove(@PathVariable("id") Long id,
                                                                        HttpServletRequest httpServletRequest){

        // status must be pending
        return tourRequestService.updateTourRequestApprove(id,httpServletRequest);
    }

    @PatchMapping("/{id}/decline") // http://localhost:8080/tour-requests/2/decline + PATCH + JSON
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<TourRequestResponse> updateTourRequestDecline(
                                                                         @PathVariable("id") Long id,
                                                                         HttpServletRequest httpServletRequest){

        // status must be pending
        return tourRequestService.updateTourRequestDecline(id,httpServletRequest);
    }

    @DeleteMapping("/{id}") // http://localhost:8080/tour-requests/2 + DELETE
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<TourRequestResponse> deleteTourRequest(@PathVariable("id") Long id,
                                                                  HttpServletRequest httpServletRequest){
        return tourRequestService.deleteTourRequest(id,httpServletRequest);
    }

    @GetMapping("/{id}") //localhost:8080/tour-requests/1 + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseMessage<List<TourRequestResponse>>getTourRequestById(@PathVariable Long id, HttpServletRequest request){
        return tourRequestService.getTourRequestByAdvertId(id,request);
    }
}