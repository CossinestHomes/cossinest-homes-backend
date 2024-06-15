package com.cossinest.homes.domain.concretes.business;

import com.cossinest.homes.domain.concretes.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

@Entity
@Table(name = "favorites")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ssXXX", timezone = "US")
    @Column(nullable = false)
    private LocalDateTime create_at;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name="advert_id")
    @ManyToOne
    private Advert advert;
}

