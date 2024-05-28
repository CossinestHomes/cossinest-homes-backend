package com.cossinest.homes.domain.concretes.business;


import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        /*
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

    //TODO: advertType, country, city, district, category



}
