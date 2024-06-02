package com.cossinest.homes.service.business;


import com.cossinest.homes.payload.mappers.AdvertMapper;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.repository.business.AdvertRepository;
import com.cossinest.homes.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final PageableHelper pageableHelper;

    private final AdvertRepository advertRepository;

    private final AdvertMapper advertMapper;
    public Page<AdvertResponse> getAllAdvertsByPage(Long categoryId, int advertTypeId, Double priceStart, Double priceEnd, String location, int status, int page, int size, String sort, String type) {

        Pageable pageable=pageableHelper.getPageableWithProperties(page,size,sort,type);

        return advertRepository.findByAdvertByQuery(categoryId,advertTypeId,priceStart,priceEnd,status,location,pageable).map(advertMapper::mapAdvertToAdvertResponse);

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
