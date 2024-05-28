package com.cossinest.homes.service.business;


import com.cossinest.homes.payload.request.business.TourRequestRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import com.cossinest.homes.repository.business.TourRequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TourRequestService {


    private final TourRequestRepository tourRequestRepository;

    public ResponseMessage<TourRequestResponse> saveTourRequest(TourRequestRequest tourRequestRequest) {

        // Date cakismasi var mi?


        // Time cakismasi var mı?




    }
}
