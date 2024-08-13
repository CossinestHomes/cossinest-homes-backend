package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.business.Images;
import com.cossinest.homes.payload.response.business.ImagesResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Data
@Component
@RequiredArgsConstructor
public class ImageMapper {


    public ImagesResponse toImageResponse(Images savedImage) {
        return ImagesResponse.builder()
                .id(savedImage.getId())
                .name(savedImage.getName())
                .type(savedImage.getType())
                .featured(savedImage.isFeatured())
                .data(encodeImage(savedImage.getData()))
                .advertId(savedImage.getAdvert().getId())
                .build();
    }

    private String encodeImage(byte[] imageData) {
        return Base64.getEncoder().encodeToString(imageData);
    }
}


