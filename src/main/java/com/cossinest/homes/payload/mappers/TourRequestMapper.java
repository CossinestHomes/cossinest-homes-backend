package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TourRequestMapper {

    public TourRequestResponse tourRequestToTourRequestResponse(TourRequest tourRequest){
        return TourRequestResponse.builder()
                .tourDate(tourRequest.getTourDate())
                .tourTime(tourRequest.getTourTime())
                .guestUserId(tourRequest.getGuestUserId())
                .ownerUserId(tourRequest.getOwnerUserId())
                .status(tourRequest.getStatus())
                .id(tourRequest.getId())
                .createAt(tourRequest.getCreateAt())
                .updateAt(tourRequest.getUpdateAt())
                .build();
    }

    public TourRequest tourRequestResponseToTourRequest(TourRequestResponse tourRequestResponse){
        return TourRequest.builder()
                .tourDate(tourRequestResponse.getTourDate())
                .tourTime(tourRequestResponse.getTourTime())
                .status(tourRequestResponse.getStatus())
                .createAt(tourRequestResponse.getCreateAt())
                .guestUserId(tourRequestResponse.getGuestUserId())
                .ownerUserId(tourRequestResponse.getOwnerUserId())
                .updateAt(tourRequestResponse.getUpdateAt())
                .advertId(tourRequestResponse.getAdvertId())
                .id(tourRequestResponse.getId())
                .build();
    }
}
