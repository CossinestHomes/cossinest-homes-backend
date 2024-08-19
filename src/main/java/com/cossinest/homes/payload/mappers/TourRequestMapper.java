package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.payload.request.business.TourRequestRequest;
import com.cossinest.homes.payload.response.business.TourRequestResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TourRequestMapper {


    public TourRequestResponse tourRequestToTourRequestResponse(TourRequest tourRequest) {
        return TourRequestResponse.builder()
                .advertId(tourRequest.getAdvert())
                .advertTitle(tourRequest.getAdvert().getTitle())
                .advertFeaturedImage(tourRequest.getAdvert().getFeaturedImage())
                .advertCountry(tourRequest.getAdvert().getCountry())
                .advertCity(tourRequest.getAdvert().getCity())
                .advertDistrict(tourRequest.getAdvert().getDistrict())
                .tourDate(tourRequest.getTourDate())
                .tourTime(tourRequest.getTourTime())
                .guestUserId(tourRequest.getGuestUser())
                .ownerUserId(tourRequest.getOwnerUser())
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
                .advert(advert)
                .build();
    }
}