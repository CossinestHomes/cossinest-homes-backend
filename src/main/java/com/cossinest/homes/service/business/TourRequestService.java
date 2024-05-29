package com.cossinest.homes.service.business;


import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import com.cossinest.homes.repository.business.TourRequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TourRequestService {


    private final TourRequestRepository tourRequestRepository;

    public ResponseMessage<TourRequestResponse> saveTourRequest(TourRequestRequest tourRequestRequest) {

        // Date-time cakismasi var mi?
         Boolean tourRequestsFromRepoOnTheDateByTheTime =tourRequestRepository.isExistsOnDateByTime(tourRequestRequest.getTourDate(),tourRequestRequest.getTourTime().getHour());




    }
}
