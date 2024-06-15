package com.cossinest.homes.payload.response.business;

import lombok.*;

import java.time.LocalDateTime;



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




}
