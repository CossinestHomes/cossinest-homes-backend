package com.cossinest.homes.payload.response.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ImagesResponse {

    private Long id;
    private String name;
    private String type;
    private boolean featured;
    private Long advertId;
    private String data;

}
