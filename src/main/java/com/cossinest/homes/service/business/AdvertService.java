package com.cossinest.homes.service.business;


import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.AdvertMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.business.CategoryForAdvertResponse;
import com.cossinest.homes.repository.business.AdvertRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final PageableHelper pageableHelper;

    private final AdvertRepository advertRepository;

    private final AdvertMapper advertMapper;

    private final CategoryService categoryService;

    private final MethodHelper methodHelper;



    public List<Advert> getAllAdverts(){
        return advertRepository.findAll();
    }

    public Advert getAdvertForFaavorites(Long id){
        return advertRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.ADVERT_NOT_FOUND));
    }


    public Page<AdvertResponse> getAllAdvertsByPage(Long categoryId, int advertTypeId, Double priceStart, Double priceEnd, String location, int status, int page, int size, String sort, String type) {

        Pageable pageable=pageableHelper.getPageableWithProperties(page,size,sort,type);

        return advertRepository.findByAdvertByQuery(categoryId,advertTypeId,priceStart,priceEnd,status,location,pageable).map(advertMapper::mapAdvertToAdvertResponse);

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


    /*
    ADVERT KAYDEDİLİRKEN BU KISIM KULLANILACAK
    @Transactional
    public Advert saveAdvert(Advert advert) {
        advert = advertRepository.save(advert);
        advert.generateSlug();
        return advertRepository.save(advert);
    }
     */
}
