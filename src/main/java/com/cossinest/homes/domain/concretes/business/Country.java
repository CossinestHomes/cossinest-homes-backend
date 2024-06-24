package com.cossinest.homes.domain.concretes.business;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

public class Country {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private com.cossinest.homes.domain.enums.Country name;


    @OneToMany (mappedBy = "country",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<City> city= new HashSet<>();


    @OneToMany (mappedBy = "country",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Advert> advertList = new ArrayList<>();



}
