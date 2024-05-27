package com.cossinest.homes.domain.concretes.business;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CategoryPropertyValues {





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;



    private String value;


    private Integer advert_id;



    @JoinColumn(name="category_Property_Key_id")
    @OneToOne
    private CategoryPropertyKeys categoryPropertyKeys;

    @JoinColumn(name="advert_id")
    @ManyToMany
    private Adverts adverts;


}
