package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.payload.request.business.AdvertRequest;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AdvertMapper {


    //Advert==>DTO
    public AdvertResponse mapAdvertToAdvertResponse (Advert advert){
        return AdvertResponse.builder()
                .id(advert.getId())
                .price(advert.getPrice())
                .slug(advert.getSlug())
                .desc(advert.getDesc())
                .title(advert.getTitle())
                .status(advert.getStatus())
                .createAt(advert.getCreatedAt())
                .updateAt(advert.getUpdatedAt())
                .location(advert.getLocation())
                .isActive(advert.getIsActive())
                .viewCount(advert.getViewCount())
                .build();
    }


    //DTO==>Advert
    public Advert mapAdvertRequestToAdvert(AdvertRequest advertRequest){
        return Advert.builder()
                .title(advertRequest.getTitle())
                .desc(advertRequest.getDesc())
                .builtIn(advertRequest.getBuiltIn())
                .price(advertRequest.getPrice())
                .slug(advertRequest.getSlug())
                .status(advertRequest.getStatus())
                .viewCount(advertRequest.getViewCount())
                .location(advertRequest.getLocation())
                .isActive(advertRequest.getIsActive())
                .build();
    }
}
