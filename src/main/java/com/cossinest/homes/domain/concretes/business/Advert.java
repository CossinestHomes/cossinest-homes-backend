package com.cossinest.homes.domain.concretes.business;


import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.Status;
import com.cossinest.homes.service.helper.SlugUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "adverts")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;


    @Column(nullable = false,length = 150)
    private String title;

    @Column(length = 300)
    private String description;

    private String slug;//TODO:Tekrar bakılacak

    @Column(nullable = false)
    private Double price= 0.0;

  //  @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private int status = Status.PENDING.getValue();


    @Column(name = "built_in")
    private Boolean builtIn=false;

    @Column(name = "is_active")
    private Boolean isActive=true;

    @Column(name = "view_count",nullable = false)
    private Integer viewCount;

    private String location;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Turkey")
    @Column(name = "create_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Turkey")
    @Column(name = "update_at")
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category category;


    //generate unique slug
    @PostPersist
    @PostUpdate
    public void generateSlug() {
        if (this.slug == null) {
            this.slug = SlugUtils.toSlug(this.title) + "-" + this.id;
        }
    }


    @OneToMany(mappedBy = "advert",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Favorites> favoritesList;

    @OneToMany(mappedBy = "advertId",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<TourRequest> tourRequestList;

    @OneToMany(mappedBy = "adverts",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<CategoryPropertyValue> categoryPropertyValuesList;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "advert_type_id")
    private AdvertType advertType;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "district_id")
    private District district;

    @OneToMany(mappedBy = "advert",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Images> imagesList;

    @OneToMany(mappedBy = "advertId",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Log> logList;










}
