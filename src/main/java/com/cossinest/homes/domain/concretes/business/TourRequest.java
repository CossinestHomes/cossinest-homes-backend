package com.cossinest.homes.domain.concretes.business;

import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "advert_id")
    @ManyToOne
    @JsonIgnore  // Advert ile check et
    private Advert advertId;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "ownerUser_id")
    @JsonIgnore
    private User ownerUserId;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "guestUser_id")
    @JsonIgnore
    private User guestUserId;





}
