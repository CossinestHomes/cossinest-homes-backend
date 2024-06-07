package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.City;
import com.cossinest.homes.domain.concretes.business.Country;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.payload.request.business.AdvertRequest;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.business.CategoryForAdvertResponse;
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
                .countryId(advert.getCountry().getId())
                .cityId(advert.getCity().getId())
                //.district
                //.images
                //.advert_type
                .categoryId(advert.getCategory().getId())
                .userId(advert.getUser().getId())
                .build();
    }


    //DTO==>Advert
    public Advert mapAdvertRequestToAdvert(AdvertRequest advertRequest, Category category, City city, User user, Country country){
        return Advert.builder()
                .title(advertRequest.getTitle())
                .desc(advertRequest.getDesc())
                .builtIn(advertRequest.getBuiltIn())
                .price(advertRequest.getPrice())
                .status(advertRequest.getStatus())
                .viewCount(advertRequest.getViewCount())
                .location(advertRequest.getLocation())
                .isActive(advertRequest.getIsActive())
                .slug(advertRequest.getSlug())
                .category(category)
                .city(city)
                .user(user)
                .country(country)
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
