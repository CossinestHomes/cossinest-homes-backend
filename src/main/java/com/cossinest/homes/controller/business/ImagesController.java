package com.cossinest.homes.controller.business;

import com.cossinest.homes.domain.concretes.business.Images;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.response.business.ImagesResponse;
import com.cossinest.homes.service.business.ImagesService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/images")
public class ImagesController {

    private final ImagesService imagesService;

    @GetMapping("{imageId}") // http://localhost:8080/images/1
    public ResponseEntity<byte[]> getImageById(@PathVariable("imageId") Long id){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.add("Content-Type","images/png");

         byte[] image = imagesService.getImageById(id).map(Images::getData).orElseThrow(()->
                 new ResourceNotFoundException(ErrorMessages.NOT_FOUND_IMAGE));

         return new ResponseEntity<>(image, httpHeaders,HttpStatus.OK);
       //  return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);

    }


//    @PostMapping("/{advertId}") // http://localhost:8080/images/1
//    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
//    public ResponseEntity<List<Long>> uploadImages(@PathVariable("id") Long advertId, @RequestParam("files") MultipartFile[] files) {
//
//        return ResponseEntity.ok(imagesService.uploadImages(advertId,files));
//
//    }

    @PostMapping("/{advertId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<List<ImagesResponse>> uploadImages(@PathVariable("advertId") Long advertId, @RequestParam("files") MultipartFile[] files) {
        return ResponseEntity.ok(imagesService.uploadImages(advertId, files));
    }


    @DeleteMapping("/{image_ids}")  // http://localhost:8080/images/5,56,22,56,7
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<String> deleteImages(@PathVariable("image_ids") List<Long> ids){

        imagesService.deleteImages(ids);

        return ResponseEntity.ok(SuccesMessages.IMAGE_DELETED_SUCCESSFULLY);
    }

    @PutMapping("/{imageId}") // http://localhost:8080/images/1
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<byte[]> updateFeaturedOfImage(@PathVariable("imageId") Long imageId){

       return ResponseEntity.ok(imagesService.updateFeaturedOfImage(imageId)) ;
    }

}
