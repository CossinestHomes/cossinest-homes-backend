package com.cossinest.homes.contactMessage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull
    @Size(max = 30 , message = "Your name must be a maximum of 30 characters.")
    private String first_name;

    @NotNull
    @Size(max = 30 , message = "Your lastname must be a maximum of 30 characters.")
    private String last_name;

    @NotNull
    @Size(max = 60 , message = "Your email must be a maximum of 60 characters.")
    @Email
    private String email;

    @NotNull
    @Size(max = 300 , message = "Your message must be a maximum of 300 characters.")
    private String message;

    @NotNull
    private int status=0;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm" , timezone = "US")
    private LocalDateTime create_at;



}
