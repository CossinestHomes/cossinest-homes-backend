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

    final TourRequestService tourRequestService;

    @PostMapping // http://localhost:8080/tour-requests  + POST + JSON
    public ResponseMessage<TourRequestResponse> save(@Valid @RequestBody TourRequestRequest tourRequestRequest,
                                                     HttpServletRequest httpServletRequest){
        //HttpServletRequest ile giriş yapan kullanıcı id'si atanacak

       return tourRequestService.saveTourRequest(tourRequestRequest,httpServletRequest);
    }

    @GetMapping("/auth") // http://localhost:8080/tour-requests/auth?page=0&size=7&sort=tour_date&type=asc  + GET
    public ResponseMessage<Page<TourRequestResponse>> getAllTourRequestByPageAuth(
            HttpServletRequest httpServletRequest,
    @RequestParam("page") int page,
    @RequestParam("size") int size,
    @RequestParam("sort") String sort,
    @RequestParam("type") String type){

        return tourRequestService.getAllTourRequestByPageAuth(httpServletRequest,page, size,sort,type);
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

}
