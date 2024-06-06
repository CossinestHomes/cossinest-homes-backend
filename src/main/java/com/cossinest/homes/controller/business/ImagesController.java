package com.cossinest.homes.controller.business;

import com.cossinest.homes.domain.concretes.business.Images;
import com.cossinest.homes.service.business.ImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/images")
public class ImagesController {

    private final ImagesService imagesService;

    @GetMapping("{id}") // http://localhost:8080/images/1
    public ResponseEntity<Images> getImageById(@PathVariable("id") Long id){

        return ResponseEntity.ok(imagesService.getImageById(id));
    }

    @PostMapping("/")

    //deneme

    //deneme 2
    // deneme 3
    //


}
