package com.cossinest.homes.payload.response.business;

import com.cossinest.homes.domain.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TourRequestResponse {


    private Long id;
    private LocalDate tour_date;
    private LocalDateTime tour_time;
    private StatusType status;
    private LocalDateTime create_at;
    private LocalDateTime update_at;



}
