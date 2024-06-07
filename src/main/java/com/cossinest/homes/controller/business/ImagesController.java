package com.cossinest.homes.controller.business;

import com.cossinest.homes.domain.concretes.business.Images;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.response.business.ImagesResponse;
import com.cossinest.homes.service.business.ImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;

@RestController
@RequiredArgsConstructor

@RequestMapping("/images")
public class ImagesController {

    private final ImagesService imagesService;

    @GetMapping("{imageId}") // http://localhost:8080/images/1
    public ResponseEntity<byte[]> getImageById(@PathVariable("imageId") Long id){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","images/png");

         byte[] image = imagesService.getImageById(id).map(Images::getData).orElseThrow(()->
                 new ResourceNotFoundException(ErrorMessages.NOT_FOUND_IMAGE));

         return new ResponseEntity<>(image, httpHeaders,HttpStatus.OK);
    }

    @PostMapping("{advertId}")
    public ResponseEntity<ImagesResponse> uploadImages(@PathVariable("advertId") Long id, MultipartFile file) throws IOException {
        return imagesService.uploadImages(id,file);
    }




}
