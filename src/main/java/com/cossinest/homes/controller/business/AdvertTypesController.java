package com.cossinest.homes.controller.business;

import com.cossinest.homes.domain.concretes.business.AdvertType;
import com.cossinest.homes.payload.request.business.AdvertTypeRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.AdvertTypeResponse;
import com.cossinest.homes.service.business.AdvertTypesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/advert-types")
@RequiredArgsConstructor
public class AdvertTypesController {

    private final AdvertTypesService advertTypesService;

    @GetMapping("/advert-types")
    public List<AdvertTypeResponse> getAllAdvertTypes(){
        return advertTypesService.getAllAdvertTypes();
    }

    @GetMapping("/advert-types/{id}")
    //TODO: manager ve admin yapacak
    public AdvertTypeResponse getAdvertTypeById(@PathVariable Long id){
            return advertTypesService.getAdvertTypeById(id);
    }

    @PostMapping("/advert-types")
    //TODO: manager ve admin yapacak
    public ResponseMessage<AdvertTypeResponse> createAdvertType(@Valid @RequestBody AdvertTypeRequest advertTypeRequest){
        return advertTypesService.saveAdvertType(advertTypeRequest);
    }

    @PutMapping("/advert-types/{id}")
    //TODO: manager ve admin yapacak
    public ResponseEntity<AdvertTypeResponse> updateAdvertTypeById(@PathVariable Long id, @RequestBody AdvertTypeRequest advertTypeRequest){
        return ResponseEntity.ok(advertTypesService.updateAdvertTypeById(id, advertTypeRequest));
    }

    @DeleteMapping("/advert-types/{id}")
    //TODO: manager ve admin yapacak
    public ResponseMessage deleteAdvertType(@PathVariable Long id){
        return advertTypesService.deleteAdvertTypeById(id);
    }

}
