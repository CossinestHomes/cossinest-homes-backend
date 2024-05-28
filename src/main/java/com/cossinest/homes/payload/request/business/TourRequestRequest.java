package com.cossinest.homes.payload.request.business;

import com.cossinest.homes.domain.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TourRequestRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="dd-MM-yyyy" )
    @NotNull(message = "Tour date can not be empty")
    private LocalDate tourDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @NotNull(message = "Tour date can not be empty")
    @FutureOrPresent(message = "Tour time must be today or in the future")
    private LocalDateTime tourTime;

    @NotNull(message = "Advert can not be empty")
    private int advertId; // Adverts datatype


}
