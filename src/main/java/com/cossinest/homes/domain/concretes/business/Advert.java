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
    private String desc;

    private String slug;//TODO:Tekrar bakılacak

    @Column(nullable = false)
    private Double price= 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status=Status.PENDING; //TODO:Tekrar bakılacak
    //kullanımı
        /*/
    public class Main {
    public static void main(String[] args) {
        Status status = Status.PENDING;
        int value = status.getValue();
        String description = status.getDescription();

        System.out.println("Value: " + value); // Output: Value: 0
        System.out.println("Description: " + description); // Output: Description: Pending
    }
}
     */

    @Column(name = "built_in")
    private Boolean builtIn;

    @Column(name = "is_active")
    private Boolean isActive;

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
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category category;


    //generate unique slug
    @PostPersist
    public void generateSlug() {
        if (this.slug == null) {
            this.slug = SlugUtils.toSlug(this.title) + "-" + this.id;
        }
    }

    //TODO: advertType, country, district,images

    @OneToMany(mappedBy = "advert",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Favorites> favoritesList;

    @OneToMany(mappedBy = "advert",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<TourRequest> tourRequestList;

    @OneToMany(mappedBy = "advert",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<CategoryPropertyValues> categoryPropertyValuesList;

    //TODO: logs





}
