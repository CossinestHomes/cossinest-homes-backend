package com.cossinest.homes.payload.response.business;

import com.cossinest.homes.domain.concretes.business.Images;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
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

    private ImagesResponse images;



    //TODO: favourites


}
