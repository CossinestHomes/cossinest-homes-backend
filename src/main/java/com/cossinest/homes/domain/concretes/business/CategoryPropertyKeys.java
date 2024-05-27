package com.cossinest.homes.domain.concretes.business;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CategoryPropertyKeys {





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;


    @NotNull(message = "name can not be null")
    @NotBlank(message = "name can not be white space")
    @Size(min=2, max=80, message = "name '${validatedValue}' must be between {min} and {max} long")
    @Column(nullable = false, length = 80)
    private String name;


    private boolean built_in;


    @JsonIgnore // sonsuz döngüye girilmesin diye @JsonIgnore eklendi
    @ManyToOne
    @JoinColumn(name="category_id") // Bu anotasyon ile CategoryPropertyKeys table'ina ismi "category_id" olan bir Sutun ekliyoruz
    private Categories categories;

    @JsonIgnore
    @OneToOne(mappedBy = "categoryPropertyKeys")
    private CategoryPropertyValues categoryPropertyValues;

}
