package com.cossinest.homes.payload.request.business;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.File;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ImagesRequest {


    private List<File> Images;

@NotNull(message = "Image'sadvert_id can not be empty")
    private Long Advert_id;
}
