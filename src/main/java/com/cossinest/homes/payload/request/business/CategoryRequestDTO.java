package com.cossinest.homes.payload.request.business;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryRequestDTO {



    private Long id;

    @NotNull(message = "title can not be null")
    @NotBlank(message = "title can not be white space")
    @Size(min=2, max=150, message = "title '${validatedValue}' must be between {min} and {max} long")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Title must consist of the characters .")
    private String title;

    @NotNull(message = "name can not be null")
    @NotBlank(message = "name can not be white space")
    @Size(min=2, max=80, message = "name '${validatedValue}' must be between {min} and {max} long")
    @Column(nullable = false, length = 80)
    private String name;


    @NotNull(message = "icon can not be null")
    @NotBlank(message = "icon can not be white space")
    @Size(min=2, max=50, message = "title '${validatedValue}' must be between {min} and {max} long")
    private String icon;

    @NotNull(message = "seq can not be null")
    @NotBlank(message = "seq can not be white space")
    private Integer seq;

    @NotNull(message = "slug can not be null")
    @NotBlank(message = "slug can not be white space")
    @Size(min=5, max=200, message = "slug '${validatedValue}' must be between {min} and {max} long")
    private String slug;

    @NotNull(message = "is_active can not be null")
    @NotBlank(message = "is_active can not be white space")
    @Column(name = "is_active")
    private boolean active;









}
