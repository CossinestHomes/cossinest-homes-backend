package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Images;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.response.business.ImagesResponse;
import com.cossinest.homes.repository.business.ImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImagesService {

    private final ImagesRepository imagesRepository;

    public Optional<Images> getImageById(Long id) {
       return imagesRepository.findById(id);
    }

    public ResponseEntity<ImagesResponse> uploadImages(Long id, MultipartFile file) throws IOException {




    }
}
