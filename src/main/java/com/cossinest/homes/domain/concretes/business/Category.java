package com.cossinest.homes.domain.concretes.business;


import com.cossinest.homes.service.helper.SlugUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name="categories")
public class Category {




 /*     OneToMany       --> LAZY    // SAG Taraf MANY ise LAZY yapiyor (DEFAULT Olarak)     MANY -- MAZY -- LAZY
        ManyToMany      --> LAZY    // SAG Taraf MANY ise LAZY yapiyor (DEFAULT Olarak)
        OneToOne        --> EAGER   // SAG Taraf ONE ise EAGER yapiyor (DEFAULT Olarak)
        ManyToOne       --> EAGER   // SAG Taraf ONE ise EAGER yapiyor (DEFAULT Olarak)                             */




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;


    @NotNull(message = "title can not be null")
    @NotBlank(message = "title can not be white space")
    @Size(min=2, max=150, message = "title '${validatedValue}' must be between {min} and {max} long")
    @Column(nullable = false, length = 150)
    private String title;


    @NotNull(message = "icon can not be null")
    @NotBlank(message = "icon can not be white space")
    @Size(min=2, max=50, message = "title '${validatedValue}' must be between {min} and {max} long")
    @Column(nullable = false, length = 50)
    private String icon;


    @Column(name = "built_in")
    private Boolean builtIn = false;


    @NotNull(message = "seq can not be null")
    @NotBlank(message = "seq can not be white space")
    private Integer seq = 0;


    @NotNull(message = "slug can not be null")
    @NotBlank(message = "slug can not be white space")
    @Size(min=5, max=200, message = "slug '${validatedValue}' must be between {min} and {max} long")
    @Column(nullable = false, length = 200)
    private String slug;


    @NotNull(message = "is_active can not be null")
    @NotBlank(message = "is_active can not be white space")
    @Column(name = "is_active")
    private Boolean active = true;


    @NotNull(message = "create_at can not be null")
    @NotBlank(message = "create_at can not be white space")
    @Setter(AccessLevel.NONE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Turkey")
    @Column(nullable = false, name="create_at")
    private LocalDateTime createdAt;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Turkey")
    @Column(nullable = true, name="update_at")
    private LocalDateTime updatedAt;



    @PrePersist                 // !!! Create ve Update edilme tarihinin persist edildigi (kalıcı hale getirildiği)  zaman olsun
    public void prePersistCreate()
    { createdAt = LocalDateTime.now();   updatedAt = LocalDateTime.now();  }



    @OneToMany(mappedBy = "category")
    private List<CategoryPropertyKey> categoryPropertyKeys = new ArrayList<>();


    @OneToMany(mappedBy = "category")
    private List<Advert> adverts = new ArrayList<>();


    //generate unique slug :

    @PostPersist
    public void generateSlug() {
        if (this.slug == null) {
            this.slug = SlugUtils.toSlug(this.title) + "-" + this.id;
        }
    }


}
