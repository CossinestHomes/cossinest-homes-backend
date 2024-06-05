package com.cossinest.homes.payload.response.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Setter;

import java.time.LocalDateTime;

public class CategoryResponse {





    private String title;



    private String icon;



    private boolean built_in;



    private Integer seq;



    private String slug;



    private boolean active;



    private LocalDateTime createdAt;



    private LocalDateTime updatedAt;




}
