package com.cossinest.homes.domain.concretes.business;


import com.cossinest.homes.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    private String title;

    private String desc;

    private String slug;

    private Double price;

    @Enumerated(EnumType.STRING)
    private Status status;
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

    @Column(name = "view_count")
    private Integer viewCount;

    private String location;

    @NotNull(message = "Please enter the create time and date")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm", timezone = "US")
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @NotNull(message = "Please enter the update time and date")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm", timezone = "US")
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    //TODO: advertType, country, city, district, user, category



}
