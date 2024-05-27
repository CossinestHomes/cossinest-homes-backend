package com.cossinest.homes.domain.concretes.business;

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
    private LocalDate tour_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm",timezone = "US")
    @Column(nullable = false)
    private LocalDateTime tour_time;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Column(nullable = false)
    private LocalDateTime create_at;

    private LocalDateTime update_at;

    @Column(nullable = false)
    private int advert_id; // Adverts datatype

    @Column(nullable = false)
    private int owner_user_id; // User datatype

    @Column(nullable = false)
    private int guest_user_id; // User datatype





}
