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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tour-requests") // http://localhost:8080/tour-requests
@RequiredArgsConstructor
public class TourRequestController {

    private final TourRequestService tourRequestService;

    @PostMapping // http://localhost:8080/tour-requests  + POST + JSON
    public ResponseMessage<TourRequestResponse> save(@Valid @RequestBody TourRequestRequest tourRequestRequest, HttpServletRequest httpServletRequest){

        return tourRequestService.saveTourRequest(tourRequestRequest,httpServletRequest);
    }

    @GetMapping("/auth") // http://localhost:8080/tour-requests/auth?page=0&size=7&sort=tour_date&type=asc  + GET
    public ResponseMessage<Page<TourRequestResponse>> getAllTourRequestByPageAuth(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sort",defaultValue = "tourDate") String sort,
            @RequestParam(value = "type",defaultValue = "asc") String type,
            @RequestParam(value = "createAt",required = false) String createAt,
            @RequestParam(value = "tourTime",required = false) String tourTime,
            @RequestParam(value = "status",required = false) String status,
            @RequestParam(value = "tourDate",required = false) String tourDate){

        return tourRequestService.getAllTourRequestByPageAuth(httpServletRequest,page, size,sort,type,createAt,tourTime,status,tourDate);
    }

    @GetMapping("/admin") // http://localhost:8080/tour-requests/admin?page=0&size=7&sort=tour_date&type=asc + GET
    public ResponseEntity<Page<TourRequestResponse>> getAllTourRequestByPageAdmin(
            HttpServletRequest httpServletRequest,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestParam("type") String type)
    {
        return tourRequestService.getAllTourRequestByPageAdmin(httpServletRequest,page, size,sort,type);
    }

    @GetMapping("/{id}/auth") // http://localhost:8080/tour-requests/3/auth + GET
    public ResponseEntity<TourRequestResponse> getTourRequestByIdAuth(@PathVariable Long id,
                                                                      HttpServletRequest httpServletRequest){
        return tourRequestService.getTourRequestByIdAuth(id,httpServletRequest);

    }

    @GetMapping("/{id}/admin") // http://localhost:8080/tour-requests/2/admin + GET
    public ResponseEntity<TourRequestResponse> getTourRequestByIdAdmin(@PathVariable Long id,
                                                                       HttpServletRequest httpServletRequest){
        return tourRequestService.getTourRequestByIdAdmin(id,httpServletRequest);
    }

    @PutMapping("/{id}/auth") // http://localhost:8080/tour-requests/2/auth + PUT + JSON
    public ResponseMessage<TourRequestResponse> updateTourRequestAuth(@Valid @RequestBody TourRequestRequest tourRequestRequest,
                                                                      @PathVariable("id") Long id,
                                                                      HttpServletRequest httpServletRequest){

        // status must be pending
        return tourRequestService.updateTourRequestAuth(tourRequestRequest,id,httpServletRequest);
    }

    @PatchMapping("/{id}/cancel") // http://localhost:8080/tour-requests/2/cancel + PATCH + JSON
    public ResponseMessage<TourRequestResponse> updateTourRequestCancel(@Valid @RequestBody TourRequestRequest tourRequestRequest,
                                                                      @PathVariable("id") Long id,
                                                                      HttpServletRequest httpServletRequest){

        // status must be pending
        return tourRequestService.updateTourRequestCancel(tourRequestRequest,id,httpServletRequest);
    }

    @PatchMapping("/{id}/approve") // http://localhost:8080/tour-requests/2/approve + PATCH + JSON
    public ResponseMessage<TourRequestResponse> updateTourRequestApprove(@Valid @RequestBody TourRequestRequest tourRequestRequest,
                                                                        @PathVariable("id") Long id,
                                                                        HttpServletRequest httpServletRequest){

        // status must be pending
        return tourRequestService.updateTourRequestApprove(tourRequestRequest,id,httpServletRequest);
    }

    @PatchMapping("/{id}/decline") // http://localhost:8080/tour-requests/2/decline + PATCH + JSON
    public ResponseMessage<TourRequestResponse> updateTourRequestDecline(@Valid @RequestBody TourRequestRequest tourRequestRequest,
                                                                         @PathVariable("id") Long id,
                                                                         HttpServletRequest httpServletRequest){

        // status must be pending
        return tourRequestService.updateTourRequestDecline(tourRequestRequest,id,httpServletRequest);
    }

    @DeleteMapping("/{id}") // http://localhost:8080/tour-requests/2 + DELETE
    public ResponseMessage<TourRequestResponse> deleteTourRequest(@PathVariable("id") Long id,
                                                                  HttpServletRequest httpServletRequest){
        return tourRequestService.deleteTourRequest(id,httpServletRequest);
    }
}