package com.cossinest.homes.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogStaticResponse {

    private int categories;
    private int adverts;
    private int advertType;
    private int tourRequest;
    private int customer;

}
