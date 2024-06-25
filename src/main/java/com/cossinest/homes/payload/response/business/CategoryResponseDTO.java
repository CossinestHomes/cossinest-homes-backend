package com.cossinest.homes.payload.response.business;

import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class CategoryResponseDTO {



    private Long catId;


    private String title;


    private String icon;


    private boolean builtIn;


    private Integer seq;


    private String slug;


    private boolean active;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;


    private List<CategoryPropertyKey> categoryPropertyKeys;



}
