package com.cossinest.homes.payload.response.business;

import com.cossinest.homes.domain.concretes.business.CategoryPropertyKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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

    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;


    private Set<CategoryPropertyKey> categoryPropertyKeys;



}
