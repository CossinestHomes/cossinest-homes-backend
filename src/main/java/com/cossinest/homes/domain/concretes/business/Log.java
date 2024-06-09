package com.cossinest.homes.domain.concretes.business;

import com.cossinest.homes.domain.enums.LogEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Log {

    @Setter(AccessLevel.NONE)
    @Id
    private Long id;

    @ManyToOne
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @Column(name = "advert_id")
    private Long advertId;

    @Enumerated(EnumType.STRING)
    private LogEnum log;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-mm HH:mm",timezone = "Turkey")
    public LocalDateTime create_at;

}