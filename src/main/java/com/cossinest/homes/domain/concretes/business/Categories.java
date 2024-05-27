package com.cossinest.homes.domain.concretes.business;


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
@Entity
public class Categories {





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


    private boolean built_in;


    @NotNull(message = "seq can not be null")
    @NotBlank(message = "seq can not be white space")
    private Integer seq;


    @NotNull(message = "slug can not be null")
    @NotBlank(message = "slug can not be white space")
    @Size(min=5, max=200, message = "slug '${validatedValue}' must be between {min} and {max} long")
    @Column(nullable = false, length = 200)
    private String slug;


    @NotNull(message = "is_active can not be null")
    @NotBlank(message = "is_active can not be white space")
    private boolean is_active;


    @NotNull(message = "create_at can not be null")
    @NotBlank(message = "create_at can not be white space")
    @Setter(AccessLevel.NONE)
    private LocalDateTime create_at = LocalDateTime.now();


    @Column(nullable = true)
    @Setter(AccessLevel.NONE)
    private LocalDateTime update_at = LocalDateTime.now();


    @OneToMany(mappedBy = "categories")
    private List<CategoryPropertyKeys> categoryPropertyKeys = new ArrayList<>();



}
