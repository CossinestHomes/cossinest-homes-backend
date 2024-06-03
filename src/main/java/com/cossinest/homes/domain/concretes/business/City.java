package com.cossinest.homes.domain.concretes.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class City {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "City name can not be empty")
    @Column(nullable = false, length = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.cossinest.homes.domain.enums.Cities cities;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany (mappedBy = "city",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Advert> advertList = new ArrayList<>();

    //TODO:district

}
