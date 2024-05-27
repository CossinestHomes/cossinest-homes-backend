package com.cossinest.homes.payload.response.business;

import com.cossinest.homes.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvertResponse {

    private Long id;

    private String title;

    private String desc;

    private String slug;

    private Double price;

    private Status status;
    private Boolean builtIn;

    private Boolean isActive;

    private Integer viewCount;

    private String location;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    //TODO: advertType, country, city, district, user, category
}
