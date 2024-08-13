package com.cossinest.homes.domain.concretes.business;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

@Entity
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] data;

    @Column(nullable = false)
    private String name;

    private String type;

    @Column(nullable = false)
    private boolean featured;

    @ManyToOne
    @JoinColumn(name = "advert_id", nullable = false)
    private Advert advert;



}
