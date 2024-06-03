package com.cossinest.homes.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {



    private Long id;

    @NotNull(message = "title can not be null")
    @NotBlank(message = "title can not be white space")
    @Size(min=2, max=150, message = "title '${validatedValue}' must be between {min} and {max} long")
    @Column(nullable = false, length = 150)
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Title must consist of the characters .")
    private String title;

    @NotNull(message = "icon can not be null")
    @NotBlank(message = "icon can not be white space")
    @Size(min=2, max=50, message = "title '${validatedValue}' must be between {min} and {max} long")
    @Column(nullable = false, length = 50)
    private String icon;

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
    @Column(name = "is_active")
    private boolean isActive;

    @Setter(AccessLevel.NONE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Turkey")
    @Column(nullable = true, name="update_at")
    private LocalDateTime updatedAt;







}
