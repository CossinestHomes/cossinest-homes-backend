package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.business.*;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.payload.request.business.AdvertRequest;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.business.CategoryForAdvertResponse;
import com.cossinest.homes.service.business.CategoryPropertyValueService;
import com.cossinest.homes.service.helper.MethodHelper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@RequiredArgsConstructor
public class AdvertMapper {

    private CategoryPropertyValueService categoryPropertyValueService;
    private MethodHelper methodHelper;

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
                .countryId(advert.getCountry().getId())
                .cityId(advert.getCity().getId())
                .properties(methodHelper.getAdvertResponseProperties(advert,categoryPropertyValueService))
                //.district
                //.images
                .advertTypeId(advert.getAdvertType().getId())
                .categoryId(advert.getCategory().getId())
                .userId(advert.getUser().getId())
                .build();
    }


    //DTO==>Advert
    public Advert mapAdvertRequestToAdvert(AdvertRequest advertRequest, Category category, City city, User user, Country country, AdvertType advertType){
        return Advert.builder()
                .title(advertRequest.getTitle())
                .desc(advertRequest.getDesc())
                .builtIn(advertRequest.getBuiltIn())
                .price(advertRequest.getPrice())
                //.status dedault değeri ile kaydedilecek mi diye kontrol et
                .viewCount(advertRequest.getViewCount())
                .location(advertRequest.getLocation())
                .isActive(advertRequest.getIsActive())
                .slug(advertRequest.getSlug())
                .category(category)
                .city(city)
                .user(user)
                .country(country)
                .advertType(advertType)
                .build();
    }

    public Advert mapAdvertRequestToUpdateAdvert(Long id,AdvertRequest advertRequest,Category category, City city, Country country, AdvertType advertType){
        return Advert.builder()
                .id(id)
                .country(country)
                .city(city)
                .advertType(advertType)
                .slug(advertRequest.getSlug())
                .price(advertRequest.getPrice())
                .title(advertRequest.getTitle())
                .desc(advertRequest.getDesc())
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




}
