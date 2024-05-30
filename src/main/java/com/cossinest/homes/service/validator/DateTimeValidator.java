package com.cossinest.homes.service.validator;

import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import com.cossinest.homes.repository.business.TourRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class DateTimeValidator {


    public void checkConflictTourRequestFromRepoByAdvert(List<TourRequest> tourRequestsFromRepo, TourRequestRequest tourRequestRequest) {

        for(TourRequest tourRequest: tourRequestsFromRepo ){
            if((tourRequest.getAdvertId().getId().equals(tourRequestRequest.getAdvertId().getId())) &&
                    (tourRequest.getTourDate().equals(tourRequestRequest.getTourDate()))) {

               long betweenMinutesTime = calculateMinutesBetweenTime(tourRequest,tourRequestRequest);

                    if(Math.abs(betweenMinutesTime)<30){
                        // Tour sürelerini 30 dk olarak belirledim
                        throw new ConflictException(ErrorMessages.CONFLICT_TOUR_TIME);

                    }
            }

            }


        }


    public void checkConflictTourRequestFromRepoByUser(User userGuest, TourRequestRequest tourRequestRequest) {
        for(TourRequest tourRequest : userGuest.getTourRequests){
           if(tourRequest.getTourDate().equals(tourRequestRequest.getTourDate())){

               if(Math.abs(calculateMinutesBetweenTime(tourRequest,tourRequestRequest))<30){
                   // Tour sürelerini 30 dk olarak belirledim
                   throw new ConflictException(ErrorMessages.CONFLICT_TOUR_TIME);

               }
           }
        }
    }

    public long calculateMinutesBetweenTime(TourRequest tourRequest,TourRequestRequest tourRequestRequest){

        LocalTime tourTimeFromRepo = tourRequest.getTourTime();

        LocalTime requestTime = tourRequestRequest.getTourTime();

        return Duration.between(tourTimeFromRepo,requestTime).toMinutes();

    }
}


