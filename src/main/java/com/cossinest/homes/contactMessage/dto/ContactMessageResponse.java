package com.cossinest.homes.contactMessage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessageResponse {

    private String first_name;
    private String last_name;
    private String email;
    private String message;
    private LocalDateTime createdAt;
    private int status= 0;

}
