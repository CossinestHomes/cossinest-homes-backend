package com.cossinest.homes.domain.concretes.business;


import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.Status;
import com.cossinest.homes.service.helper.SlugUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private String slug;

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
    private Integer viewCount =0;

    private String location;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Turkey")
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Turkey")
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
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

    //,orphanRemoval = true
    @OneToMany(mappedBy = "advert",cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Favorites> favoritesList;

    //,orphanRemoval = true
    @OneToMany(mappedBy = "advert",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TourRequest> tourRequestList;


    //,orphanRemoval = true
    @OneToMany(mappedBy = "adverts",cascade = CascadeType.ALL)
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

    //,orphanRemoval = true
    @OneToMany(mappedBy = "advert",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Images> imagesList = new ArrayList<>();

  @OneToOne(mappedBy = "advert",cascade = CascadeType.ALL)
  @JsonIgnore
    private Images featuredImage;


    //,orphanRemoval = true
    @OneToMany(mappedBy = "advertId",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Log> logList;










}
