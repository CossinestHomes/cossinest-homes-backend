package com.cossinest.homes.payload.response.business;

import com.cossinest.homes.domain.concretes.user.User;
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
    private LocalDate tourDate;
    private LocalDateTime tourTime;
    private StatusType status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private User ownerUserId;
    private User guestUserId;



}
