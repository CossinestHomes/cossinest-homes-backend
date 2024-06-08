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
    @ManyToOne
    private byte[] data;

    @Column(nullable = false)
    private String name;

    private String type;

    @Column(nullable = false)
    private Boolean featured;

    @Column(nullable = false)
    private Long advertId;



}
