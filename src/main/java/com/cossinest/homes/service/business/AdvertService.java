package com.cossinest.homes.service.business;


import com.cossinest.homes.domain.concretes.business.*;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.AdvertMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.AdvertRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.business.CategoryForAdvertResponse;
import com.cossinest.homes.payload.response.user.UserResponse;
import com.cossinest.homes.repository.business.AdvertRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.helper.PageableHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final PageableHelper pageableHelper;

    private final AdvertRepository advertRepository;

    private final AdvertMapper advertMapper;

    private final CategoryService categoryService;

    private final MethodHelper methodHelper;

    private final CityService cityService;

    private final CountryService countryService;

    private final CategoryPropertyValueService categoryPropertyValueService;



    public List<Advert> getAllAdverts(){
        return advertRepository.findAll();
    }

    public Advert getAdvertForFaavorites(Long id){
        return advertRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.ADVERT_NOT_FOUND));
    }


    public Page<AdvertResponse> getAllAdvertsByPage(String query,Long categoryId, int advertTypeId, Double priceStart, Double priceEnd, String location, int status, int page, int size, String sort, String type) {

        Pageable pageable=pageableHelper.getPageableWithProperties(page,size,sort,type);

        if(methodHelper.priceControl(priceStart,priceEnd)){
            throw new ConflictException(ErrorMessages.START_PRICE_AND_END_PRICE_INVALID);
        }

        return advertRepository.findByAdvertByQuery(categoryId,advertTypeId,priceStart,priceEnd,status,location,query,pageable).map(advertMapper::mapAdvertToAdvertResponse);

    }

    //category
    public List<CategoryForAdvertResponse> getCategoryWithAmountForAdvert(){

       List<Category> categoryList = categoryService.getAllCategory();

       List<CategoryForAdvertResponse> categoryForAdvertList= categoryList.stream().map(advertMapper::mapperCategoryToCategoryForAdvertResponse).toList();

       return categoryForAdvertList;
    }

    public List<AdvertResponse> getPopularAdverts(int value) {

        List<Advert> advertList = getAllAdverts();


        return   advertList.stream()
                .filter(t-> methodHelper.calculatePopularityPoint(t.getTourRequestList().size(),t.getViewCount())>=value)
                .map(advertMapper::mapAdvertToAdvertResponse)
                .collect(Collectors.toList());
    }

    public Page<AdvertResponse> getAllAdvertForAuthUser(HttpServletRequest request, int page, int size, String sort, String type) {

        Pageable pageable=pageableHelper.getPageableWithProperties(page,size,sort,type);

        User user= methodHelper.getUserByHttpRequest(request);

        return advertRepository.findAdvertsForUser(user.getId(),pageable).map(advertMapper::mapAdvertToAdvertResponse);
    }

    public Page<AdvertResponse> getAllAdvertsByPageForAdmin(HttpServletRequest request,String query ,Long categoryId, int advertTypeId, Double priceStart, Double priceEnd, String location, int status, int page, int size, String sort, String type) {
        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.ADMIN, RoleType.MANAGER);
        Pageable pageable=pageableHelper.getPageableWithProperties(page,size,sort,type);

        if(methodHelper.priceControl(priceStart,priceEnd)){
            throw new ConflictException(ErrorMessages.START_PRICE_AND_END_PRICE_INVALID);
        }
        return advertRepository.findByAdvertByQuery(categoryId,advertTypeId,priceStart,priceEnd,status,location,query,pageable).map(advertMapper::mapAdvertToAdvertResponse);
    }

    public AdvertResponse getAdvertBySlug(String slug) {
        Advert advert = advertRepository.findBySlug(slug).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.ADVERT_NOT_FOUND));

        return advertMapper.mapAdvertToAdvertResponse(advert);
    }

    public ResponseEntity<AdvertResponse> getAdvertByIdForCustomer(Long id, HttpServletRequest request) {
        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user,RoleType.CUSTOMER);

        Advert advert=advertRepository.findByIdAndUser(id,user.getId()).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.ADVERT_NOT_FOUND));

        return ResponseEntity.ok(advertMapper.mapAdvertToAdvertResponse(advert));
    }

    public ResponseEntity<AdvertResponse> getAdvertByIdForAdmin(Long id, HttpServletRequest request) {
        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user,RoleType.ADMIN);

        Advert advert=advertRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.ADVERT_NOT_FOUND));

        return ResponseEntity.ok(advertMapper.mapAdvertToAdvertResponse(advert));
    }

    @Transactional
    public AdvertResponse saveAdvert(AdvertRequest advertRequest, HttpServletRequest httpServletRequest) {
        Advert advert =new Advert();
        Category category=categoryService.getCategoryById(advertRequest.getCategoryId());
        City city=cityService.getCityById(advertRequest.getCityId());
        User user=methodHelper.getUserByHttpRequest(httpServletRequest);
        Country country= countryService.getCountryById(advertRequest.getCountryId());
        List<CategoryPropertyValue> categoryPropertyValuesForDb =methodHelper.getPropertyValueList(category,advertRequest,categoryPropertyValueService);

        advert.setCategoryPropertyValuesList(categoryPropertyValuesForDb);
        advert=advertMapper.mapAdvertRequestToAdvert(advertRequest,category,city,user,country);

        advert = advertRepository.save(advert);
        advert.generateSlug();
        advertRepository.save(advert);

        return advertMapper.mapAdvertToAdvertResponse(advert);

        //district
        //advert_type
        //images

    }




    /*
    ADVERT KAYDEDİLİRKEN BU KISIM KULLANILACAK

    public Advert saveAdvert(Advert advert) {

    }
     */


}
