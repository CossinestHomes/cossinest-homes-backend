package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.business.*;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.Status;
import com.cossinest.homes.payload.request.abstracts.AbstractAdvertRequest;
import com.cossinest.homes.payload.request.business.AdvertRequestForAdmin;
import com.cossinest.homes.payload.request.business.CreateAdvertRequest;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.business.CategoryForAdvertResponse;
import com.cossinest.homes.service.business.CategoryPropertyValueService;
import com.cossinest.homes.service.helper.MethodHelper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class AdvertMapper {

    private final CategoryPropertyValueService categoryPropertyValueService;
    private final MethodHelper methodHelper;

    //Advert==>DTO
    public AdvertResponse mapAdvertToAdvertResponse (Advert advert){
        AdvertResponse.AdvertResponseBuilder builder = AdvertResponse.builder()
                .id(advert.getId())
                .price(advert.getPrice())
                .slug(advert.getSlug())
                .builtIn(advert.getBuiltIn())
                .description(advert.getDescription())
                .title(advert.getTitle())
                .status(getStatusName(advert.getStatus()))
                .createAt(advert.getCreatedAt())
                .updateAt(advert.getUpdatedAt())
                .location(advert.getLocation())
                .isActive(advert.getIsActive())
                .viewCount(advert.getViewCount())
                .countryId(advert.getCountry().getId())
                .cityId(advert.getCity().getId())
                .properties(methodHelper.getAdvertResponseProperties(advert, categoryPropertyValueService))
                .districtId(advert.getDistrict().getId())
                .imagesIdsList(advert.getImagesList())
                .advertTypeId(advert.getAdvertType().getId())
                .categoryId(advert.getCategory().getId());

        // Only set userId if it's not null
        if (advert.getUser() != null && advert.getUser().getId() != null) {
            builder.userId(advert.getUser().getId());
        }

        return builder.build();
    }

    //status response için yardımcı method
    public String getStatusName(int statusNumber){
        if(statusNumber==0){
            return Status.PENDING.name();
        }else if(statusNumber==1){
            return Status.ACTIVATED.name();
        } else if (statusNumber==2) {
            return Status.REJECTED.name();
        }else return null;
    }


    //DTO==>Advert
    public Advert mapAdvertRequestToAdvert(AbstractAdvertRequest advertRequest, Category category, City city, User user, Country country, AdvertType advertType, District district){
        return Advert.builder()
                .title(advertRequest.getTitle())
                .description(advertRequest.getDescription())
                .location(advertRequest.getLocation())
                .price(advertRequest.getPrice())
                .viewCount(0)
                .district(district)
                .category(category)
                .status(Status.PENDING.getValue())
                .city(city)
                .user(user)
                .country(country)
                .advertType(advertType)
                .build();
    }

    public Advert mapAdvertRequestToUpdateAdvert(Long id,AbstractAdvertRequest advertRequest,Category category, City city, Country country, AdvertType advertType,District district,User user){
        return Advert.builder()
                .id(id)
                .user(user)
                .country(country)
                .builtIn(false)
                .viewCount(0)
                .city(city)
                .advertType(advertType)
                .price(advertRequest.getPrice())
                .title(advertRequest.getTitle())
                .description(advertRequest.getDescription())
                .district(district)
                .status(Status.PENDING.getValue())//update de status u tekrardan pending e çek
                .category(category)
                .location(advertRequest.getLocation())
                .build();
    }
    public Advert mapAdvertRequestToUpdateAdvertForAdmin(Long id, AdvertRequestForAdmin advertRequest, Category category, City city, Country country, AdvertType advertType, District district){
        return Advert.builder()
                .id(id)
                .country(country)
                .city(city)
                .viewCount(0)
                .advertType(advertType)
                .price(advertRequest.getPrice())
                .title(advertRequest.getTitle())
                .description(advertRequest.getDescription())
                .builtIn(advertRequest.getBuiltIn())
                .district(district)
                .status(advertRequest.getStatus())
                .isActive(advertRequest.getIsActive())
                .category(category)
                .location(advertRequest.getLocation())
                .viewCount(advertRequest.getViewCount())
                .build();
    }

    //Category POJO==>DTO
    public CategoryForAdvertResponse mapperCategoryToCategoryForAdvertResponse(Category category){
        return CategoryForAdvertResponse.builder()
                .category(category.getTitle())
                .amount(category.getAdverts().size())
                .build();
    }


    public Advert mapCreateRequestToAdvert(Category category, CreateAdvertRequest createRequest, City city, Country country, AdvertType advertType, District district) {

        return Advert.builder()
                .country(country)
                .city(city)
                .advertType(advertType)
                .price(createRequest.getPrice())
                .title(createRequest.getTitle())
                .description(createRequest.getDescription())
                .status(Status.PENDING.getValue())
                .district(district)
                .category(category)
                .location(createRequest.getLocation())
                .build();
    }
}
