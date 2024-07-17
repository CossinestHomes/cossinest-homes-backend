package com.cossinest.homes.domain.concretes.business;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "advert_type")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvertType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull
    @Size(min = 2 , max = 30, message = "Min 2 chars and Max 30 chars")
    private String title;


    private Boolean builtIn;

    @OneToMany(mappedBy = "advertType",cascade = CascadeType.ALL)
    private List<Advert> advertList;

}
