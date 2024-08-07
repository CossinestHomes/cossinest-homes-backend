package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.domain.enums.StatusType;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Component
public class TourRequestMapper {
    public TourRequestResponse tourRequestToTourRequestResponse(TourRequest tourRequest) {
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

    public TourRequest tourRequestRequestToTourRequest(TourRequestRequest tourRequestRequest, Advert advert) {
        return TourRequest.builder()
                .tourDate(tourRequestRequest.getTourDate())
                .tourTime(tourRequestRequest.getTourTime())
                .advertId(advert)
                .build();
    }
}