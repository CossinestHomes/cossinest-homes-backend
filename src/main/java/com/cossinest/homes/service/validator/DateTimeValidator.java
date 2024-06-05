package com.cossinest.homes.service.validator;

import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Component
public class DateTimeValidator {


    public void checkConflictTourRequestFromRepoByAdvert(List<TourRequest> tourRequestsFromRepo, TourRequestRequest tourRequestRequest) {

        for(TourRequest tourRequest: tourRequestsFromRepo ){
            if((tourRequest.getAdvertId().getId().equals(tourRequestRequest.getAdvertId().getId())) &&  //neden id kontrolu var?
                    (tourRequest.getTourDate().equals(tourRequestRequest.getTourDate()))) {

               long betweenMinutesTime = calculateMinutesBetweenTime(tourRequest,tourRequestRequest);

                    if(Math.abs(betweenMinutesTime)<30){
                        // Tour sürelerini 30 dk olarak belirledim
                        throw new ConflictException(ErrorMessages.CONFLICT_TOUR_TIME);
                    }
            }
            }
        }

    public void checkConflictTourRequestFromRepoByUserForGuest(User userGuest, TourRequestRequest tourRequestRequest) {

        for(TourRequest tourRequest : userGuest.getTourRequests()){
           if(tourRequest.getTourDate().equals(tourRequestRequest.getTourDate())){

               if(Math.abs(calculateMinutesBetweenTime(tourRequest,tourRequestRequest))<30){
                   // Tour sürelerini 30 dk olarak belirledim
                   throw new ConflictException(ErrorMessages.CONFLICT_TOUR_TIME);

               }
           }
        }
    }
    public void checkConflictTourRequestFromRepoByUserForOwner(User ownerUser, TourRequestRequest tourRequestRequest) {

        for (TourRequest tourRequest : ownerUser.getTourRequests()){
            if(tourRequest.getTourDate().equals(tourRequestRequest.getTourDate())){

               if(calculateMinutesBetweenTime(tourRequest,tourRequestRequest) <30){
                   throw new ConflictException(ErrorMessages.CONFLICT_TOUR_TIME);

               }
            }
        }
    }

    private long calculateMinutesBetweenTime(TourRequest tourRequest,TourRequestRequest tourRequestRequest){

        LocalTime tourTimeFromRepo = tourRequest.getTourTime();

        LocalTime requestTime = tourRequestRequest.getTourTime();

        return Duration.between(tourTimeFromRepo,requestTime).toMinutes();

    }
}


