package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.Images;
import com.cossinest.homes.exception.NotLoadingCompleted;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.response.business.ImagesResponse;
import com.cossinest.homes.repository.business.ImagesRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class ImagesService {


    private ImagesRepository imagesRepository;

    private  AdvertService advertService;


     @Autowired
    public ImagesService(AdvertService advertService,ImagesRepository imagesRepository) {
        this.advertService = advertService;
        this.imagesRepository=imagesRepository;
    }


    private final String imageDirectory="/path/to/image/directory";

    public Optional<Images> getImageById(Long id) {
       return imagesRepository.findById(id);
    }


//    public List<Long> uploadImages(Long advertId, MultipartFile[] files){
//
//       Advert advert =advertService.getAdvertForFavorites(advertId);
//
//        List<Long> imageIds = new ArrayList<>();
//        boolean isFirstImage = true;
//
//        for (MultipartFile file:files) {
//            try{
//                Images image = new Images();
//
//                image.setData(file.getBytes());
//                image.setName(file.getOriginalFilename());
//                image.setType(file.getContentType());
//                image.setAdvert(advert);
//
//                if(isFirstImage){
//                    image.setFeatured(true);
//                    isFirstImage=false;
//                }else{
//                    image.setFeatured(false);
//                }
//
//               Images images =imagesRepository.save(image);
//                imageIds.add(images.getId());
//
//            }catch(IOException e){
//                throw  new NotLoadingCompleted(ErrorMessages.UPLOADING_FAILED);
//            }
//        }
//        return imageIds;
//    }

    //yardımcı metod:
    public String convertToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public List<ImagesResponse> uploadImages(Long advertId, MultipartFile[] files) {

        Advert advert = advertService.getAdvertForFavorites(advertId);
        List<ImagesResponse> imageResponses = new ArrayList<>();
        boolean isFirstImage = true;

        for (MultipartFile file : files) {
            try {
                Images image = new Images();

                image.setData(file.getBytes());
                image.setName(file.getOriginalFilename());
                image.setType(file.getContentType());
                image.setAdvert(advert);

                if (isFirstImage) {
                    image.setFeatured(true);
                    isFirstImage = false;
                } else {
                    image.setFeatured(false);
                }

                Images savedImage = imagesRepository.save(image);

                // Base64'e çevir
                String base64Data = convertToBase64(savedImage.getData());

                // ImageResponse objesi yarat
                ImagesResponse response = ImagesResponse.builder()
                        .id(savedImage.getId())
                        .name(savedImage.getName())
                        .type(savedImage.getType())
                        .featured(savedImage.isFeatured())
                        .advertId(advertId)
                        .data(base64Data)
                        .build();

                imageResponses.add(response);

            } catch (IOException e) {
                throw new NotLoadingCompleted(ErrorMessages.UPLOADING_FAILED);
            }
        }
        return imageResponses;
    }





    public void deleteImages(List<Long> ids) {


            List<Images> images = imagesRepository.findAllById(ids);

        for (Images image:images) {
            if(image==null){
                throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_IMAGE);
            }

            //Imageleri fiziksel silmek için
            Path imagePath = Paths.get(imageDirectory,image.getName());
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        imagesRepository.deleteAllById(ids);
    }

    public List<Images> getALlImages(){

        return imagesRepository.findAll();
    }

    public byte[] updateFeaturedOfImage(Long imageId) {

       Images image = imagesRepository.findById(imageId).orElseThrow(()->
               new ResourceNotFoundException(ErrorMessages.NOT_FOUND_IMAGE));

       List<Images> images = imagesRepository.findByAdvertId(image.getAdvert().getId());

        images.forEach(item -> item.setFeatured(false));

        image.setFeatured(true);

        imagesRepository.save(image);

        return image.getData();
    }

    public void resetImageTables() {
        imagesRepository.deleteAll();
    }
}
