package com.cossinest.homes.payload.response.business;

import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.cossinest.homes.domain.concretes.business.Favorites;
import com.cossinest.homes.domain.concretes.business.Images;
import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvertResponse {

    private Long id;

    private String title;

    private String description;

    private String slug;

    private Double price;

    private String status;

    private Boolean builtIn;

    private Boolean isActive;

    private Integer viewCount;

    private String location;

    private Long advertTypeId;

    private Long countryId;

    private Long cityId;

    private Long districtId;

    private Long userId;

    private Long categoryId;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Map<String,String > properties;

  //  private List<Images> imagesIdsList;

    private List<ImagesResponse> images;

    private ImagesResponse featuredImage;

    private List<Favorites> favoritesList;

    private List<TourRequest> tourRequestList;

    private Set<CategoryPropertyKey> categoryPropertyKeys;

    private int tourRequestCount;
    private int favoritesCount;

    //TODO: favourites


}
