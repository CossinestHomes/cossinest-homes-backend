package com.cossinest.homes.controller.business;

import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.business.CategoryForAdvertResponse;
import com.cossinest.homes.payload.response.business.CityForAdvertsResponse;
import com.cossinest.homes.service.business.AdvertService;
import com.cossinest.homes.service.business.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adverts")
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;
    private final CityService cityService;

    @GetMapping  //adverts?q=beyoğlu&category_id=12&advert_type_id=3&price_start=500&price_end=1500 location=34 &
                 //status=1;page=1&size=10&sort=date&type=asc
    public Page<AdvertResponse> getAllAdvertsByPage(
            @RequestParam(value = "q",required = false, defaultValue = "") String query,
            @RequestParam(value = "category_id",required = true) Long categoryId,
            @RequestParam(value = "advert_type_id",required = true) int advertTypeId,
            @RequestParam(value = "price_start",required = false) Double priceStart,
            @RequestParam(value = "price_end",required = false) Double priceEnd,
            @RequestParam(value = "location",required = false) String location,
            @RequestParam(value = "status",required = false) int status,
            @RequestParam(value = "page",required = false,defaultValue = "0") int page,
            @RequestParam(value = "size",required = false, defaultValue = "20") int size,
            @RequestParam(value = "sort",required = false,defaultValue = "category_id") String sort,
            @RequestParam(value = "type",required = false,defaultValue = "asc") String type
    ){
           return advertService.getAllAdvertsByPage(categoryId,advertTypeId,priceStart,priceEnd,location,status,page,size,sort,type);
    }

    @GetMapping("/cities")
    public ResponseMessage<List<CityForAdvertsResponse>> getAllCityWithAmountAdverts(){

        List<CityForAdvertsResponse> cityList= cityService.getAllCityForAdverts();

        return ResponseMessage.<List<CityForAdvertsResponse>>builder()
                .status(HttpStatus.OK)
                .message(SuccesMessages.RETURNED_ALL_CITIES_AND_AMOUNT)
                .object(cityList)
                .build();
    }

    @GetMapping("/categories")
    public ResponseMessage<List<CategoryForAdvertResponse>> getAllCategoryWithAmountAdverts(){
        List<CategoryForAdvertResponse> categoryList= advertService.getCategoryWithAmountForAdvert();

        return ResponseMessage.<List<CategoryForAdvertResponse>>builder()
                .status(HttpStatus.OK)
                .object(categoryList)
                .message(SuccesMessages.RETURNED_ALL_CATEGORIES_AND_AMOUNT)
                .build();
    }

    @GetMapping("/popular/{value}")
    public ResponseMessage<List<AdvertResponse>> getPopularAdverts(@PathVariable(value = "10") int value){
      List<AdvertResponse> advertResponseList = advertService.getPopularAdverts(value);

      return ResponseMessage.<List<AdvertResponse>>builder()
              .message(SuccesMessages.RETURNED_POPULAR_ADVERTS)
              .status(HttpStatus.OK)
              .object(advertResponseList)
              .build();
    }











}
