package com.cossinest.homes.domain.concretes.business;

import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.ObjectInputFilter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

@Entity
@Table(name = "tour_request")
public class TourRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="dd-MM-yyyy" )
    @Column(nullable = false)
    private LocalDate tourDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm",timezone = "US")
    @Column(nullable = false)
    private LocalDateTime tourTime;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm",timezone = "US")
    @Column(nullable = false)
    private LocalDateTime createAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm",timezone = "US")
    private LocalDateTime updateAt;

    @Column(nullable = false)
    private Advert advertId;

    @Column(nullable = false)
    private User ownerUserId;

    @Column(nullable = false)
    private User guestUserId;





}
