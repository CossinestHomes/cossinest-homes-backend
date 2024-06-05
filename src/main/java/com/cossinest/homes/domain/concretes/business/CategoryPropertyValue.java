package com.cossinest.homes.domain.concretes.business;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="category_property_values")
public class CategoryPropertyValue {





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;



    private String value;


    private Integer advert_id;


    @JsonIgnore // sonsuz döngüye girilmesin diye @JsonIgnore eklendi
    @JoinColumn(name="category_Property_Key_id")
    @ManyToOne
    private CategoryPropertyKey categoryPropertyKeys;


    @JoinColumn(name="advert_id")
    @ManyToOne
    private Advert adverts;




}
