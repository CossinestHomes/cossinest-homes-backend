package com.cossinest.homes.payload.response.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;




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




}
