package com.cossinest.homes.service.business;


import com.cossinest.homes.domain.concretes.business.*;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.LogEnum;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.domain.enums.Status;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.AdvertMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.request.abstracts.AbstractAdvertRequest;
import com.cossinest.homes.payload.request.business.AdvertRequest;
import com.cossinest.homes.payload.request.business.AdvertRequestForAdmin;
import com.cossinest.homes.payload.request.business.CreateAdvertPropertyRequest;
import com.cossinest.homes.payload.request.business.CreateAdvertRequest;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.payload.response.business.CategoryForAdvertResponse;

import com.cossinest.homes.repository.business.AdvertRepository;

import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.helper.PageableHelper;
import com.cossinest.homes.service.validator.DateTimeValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    private final CategoryPropertyKeyService categoryPropertyKeyService;

    private final AdvertTypesService advertTypesService;

    private final DateTimeValidator dateTimeValidator;

    private final LogService logService;

    private final DistrictService districtService;



    //private final ImagesService imagesService;



    public List<Advert> getAllAdverts(){
        return advertRepository.findAll();
    }

    public Advert getAdvertForFavorites(Long id){
        return advertRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.ADVERT_NOT_FOUND));
    }


    public Page<AdvertResponse> getAllAdvertsByPage(String query, Long categoryId, Long advertTypeId, Double priceStart, Double priceEnd, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        if (methodHelper.priceControl(priceStart, priceEnd)) {
            throw new ConflictException(ErrorMessages.START_PRICE_AND_END_PRICE_INVALID);
        }

        return advertRepository.findByAdvertByQuery(categoryId, advertTypeId, priceStart, priceEnd, query, pageable)
                .map(advertMapper::mapAdvertToAdvertResponse);
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

    public Page<AdvertResponse> getAllAdvertsByPageForAdmin(HttpServletRequest request,String query ,Long categoryId, Long advertTypeId, Double priceStart, Double priceEnd,int page, int size, String sort, String type) {
        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.ADMIN, RoleType.MANAGER);
        Pageable pageable=pageableHelper.getPageableWithProperties(page,size,sort,type);


        if(methodHelper.priceControl(priceStart,priceEnd)){
            throw new ConflictException(ErrorMessages.START_PRICE_AND_END_PRICE_INVALID);
        }
        return advertRepository.findByAdvertByQuery(categoryId,advertTypeId,priceStart,priceEnd,query,pageable).map(advertMapper::mapAdvertToAdvertResponse);
    }

    public AdvertResponse getAdvertBySlug(String slug) {
        Advert advert = advertRepository.findBySlug(slug).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.ADVERT_NOT_FOUND));

        return advertMapper.mapAdvertToAdvertResponse(advert);
    }

    public ResponseEntity<AdvertResponse> getAdvertByIdForCustomer(Long id, HttpServletRequest request) {

        User user = methodHelper.getUserAndCheckRoles(request,RoleType.CUSTOMER.name());

        Advert advert=isAdvertExistById(id);
        if(advert.getUser().getId()!=user.getId()){
            throw new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_IS_NOT_FOUND_FOR_USER,user.getId()));
        }

        return ResponseEntity.ok(advertMapper.mapAdvertToAdvertResponse(advert));
    }

    public ResponseEntity<AdvertResponse> getAdvertByIdForAdmin(Long id, HttpServletRequest request) {
        User user = methodHelper.getUserAndCheckRoles(request,RoleType.ADMIN.name());

        Advert advert=isAdvertExistById(id);
        return ResponseEntity.ok(advertMapper.mapAdvertToAdvertResponse(advert));
    }

    @Transactional
    public AdvertResponse saveAdvert(AdvertRequest advertRequest, HttpServletRequest httpServletRequest, MultipartFile[] files) {

        Map<String, Object> detailsMap = new HashMap<>();
     getAdvertDetails(advertRequest, httpServletRequest, detailsMap);

        Advert advert = advertMapper.mapAdvertRequestToAdvert(
                advertRequest,
                (Category) detailsMap.get("category"),
                (City) detailsMap.get("city"),
                (User) detailsMap.get("user"),
                (Country) detailsMap.get("country"),
                (AdvertType) detailsMap.get("advertType"),
                (District) detailsMap.get("district"));

        List<CategoryPropertyValue> advertValueList = new ArrayList<>();

        for (CreateAdvertPropertyRequest request1 : advertRequest.getProperties()) {
            // Request'ten gelen categoryPropertyKey'i al
            Long categoryPropertyKeyFromRequest = request1.getKeyId();

            CategoryPropertyKey categoryPropertyKeyFromDb = categoryPropertyKeyService.findPropertyKeyById(categoryPropertyKeyFromRequest);
            if (categoryPropertyKeyFromDb != null) {
                System.out.println("Found CategoryPropertyKey: " + categoryPropertyKeyFromDb.getId());

                // Yeni CategoryPropertyValue nesnesi oluştur
                CategoryPropertyValue categoryPropertyValue = new CategoryPropertyValue();

                // Request'ten gelen değeri categoryPropertyValue'ya setle
                categoryPropertyValue.setValue(request1.getValue());
                System.out.println("Setting value: " + request1.getValue());

                // CategoryPropertyKey'i categoryPropertyValue'ya setle
                categoryPropertyValue.setCategoryPropertyKeys(categoryPropertyKeyFromDb);

                // CategoryPropertyValue nesnesini kaydet
                categoryPropertyValue = categoryPropertyValueService.saveCategoryPropertyValue(categoryPropertyValue);
                System.out.println("Saved CategoryPropertyValue with ID: " + categoryPropertyValue.getId());

                // advertValueList'e ekle
                advertValueList.add(categoryPropertyValue);
                System.out.println("Added to advertValueList");
            } else {
                // Hata durumunu yönet, örneğin logla veya hata fırlat
                System.out.println("CategoryPropertyKey bulunamadı: " + categoryPropertyKeyFromRequest);
            }
        }



        advert.setCategoryPropertyValuesList(advertValueList);

        // viewCount alanını varsayılan değere ayarlayın
        if (advert.getViewCount() == null) {
            advert.setViewCount(0); // Varsayılan değer olarak 0
        }
        if (advert.getBuiltIn() == null) {
            advert.setBuiltIn(false);
        }
        if (advert.getIsActive() == null) {
            advert.setIsActive(false);
        }


        // Advert'ı kaydedin ve ID'yi elde edin
        Advert savedAdvert = advertRepository.save(advert);
        savedAdvert.generateSlug();
        advertRepository.save(savedAdvert);

        // Resimleri eklemeden önce advert_id'yi ayarlayın
        List<Images> imagesList = methodHelper.getImagesForAdvert(files, savedAdvert.getImagesList());
        for (Images image : imagesList) {
            image.setAdvert(savedAdvert); // advert_id ayarlanıyor
        }
        savedAdvert.setImagesList(imagesList);


        logService.createLogEvent(savedAdvert.getUser(), savedAdvert, LogEnum.CREATED);

        // Advert'ı ve ilişkili resimleri tekrar kaydedin
        savedAdvert = advertRepository.save(savedAdvert);




        return advertMapper.mapAdvertToAdvertResponse(savedAdvert);
    }


    @Transactional
    public AdvertResponse updateUsersAdvert(AdvertRequest advertRequest, Long id, HttpServletRequest request, MultipartFile[] files) {
        User user = methodHelper.getUserAndCheckRoles(request,RoleType.CUSTOMER.name());
        Advert advert=isAdvertExistById(id);



        if(advert.getBuiltIn()){
            throw new ResourceNotFoundException(ErrorMessages.THIS_ADVERT_DOES_NOT_UPDATE);
        }
        Map<String, Object> detailsMap = new HashMap<>();
        getAdvertDetails(advertRequest,request,detailsMap);

        List<CategoryPropertyValue>advertValueList=new ArrayList<>();

        for (CreateAdvertPropertyRequest request1 :advertRequest.getProperties()) {

            advertValueList.add(categoryPropertyValueService.categoryFindByValue(request1.getValue()));
        }
        List<Images> imagesList = methodHelper.getImagesForAdvert(files, advert != null ? advert.getImagesList() : null);
        if (imagesList != null && advert != null) {
            for (Images image : imagesList) {
                if (image != null) {
                    image.setAdvert(advert);
                }
            }
        }

        Advert updateAdvert =advertMapper.mapAdvertRequestToUpdateAdvert(id,advertRequest,
                (Category) detailsMap.get("category"),
                (City) detailsMap.get("city"),
                (Country) detailsMap.get("country"),
                (AdvertType) detailsMap.get("advertType"),
                (District) detailsMap.get("district"),
                (User) detailsMap.get("user"));
        updateAdvert.setCategoryPropertyValuesList(advertValueList);

        if (updateAdvert.getCreatedAt() == null) {
            updateAdvert.setCreatedAt(LocalDateTime.now());
        }
        updateAdvert.setUpdatedAt(LocalDateTime.now());

        updateAdvert.setIsActive(advert.getIsActive());
        updateAdvert.setSlug(advert.getSlug());
        updateAdvert.setImagesList(advert.getImagesList());

        Advert returnedAdvert=advertRepository.save(updateAdvert);



        logService.createLogEvent(advert.getUser(),advert, LogEnum.UPDATED);

        return advertMapper.mapAdvertToAdvertResponse(returnedAdvert);
    }

    @Transactional
    public AdvertResponse updateAdvert(AdvertRequestForAdmin advertRequest, Long id, HttpServletRequest request, MultipartFile[] files) {
        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.ADMIN, RoleType.MANAGER);
        Advert advert=isAdvertExistById(id);


        if(advert.getBuiltIn()){
            throw new ResourceNotFoundException(ErrorMessages.THIS_ADVERT_DOES_NOT_UPDATE);
        }

        Map<String, Object> detailsMap = new HashMap<>();
        getAdvertDetails(advertRequest,request,detailsMap);

        List<CategoryPropertyValue>advertValueList=new ArrayList<>();

        for (CreateAdvertPropertyRequest request1 :advertRequest.getProperties()) {

            advertValueList.add(categoryPropertyValueService.categoryFindByValue(request1.getValue()));
        }

        List<Images> imagesList = methodHelper.getImagesForAdvert(files, advert != null ? advert.getImagesList() : null);
        if (imagesList != null && advert != null) {
            for (Images image : imagesList) {
                if (image != null) {
                    image.setAdvert(advert);
                }
            }
        }

        Advert updateAdvert =advertMapper.mapAdvertRequestToUpdateAdvert(id,advertRequest,
                (Category) detailsMap.get("category"),
                (City) detailsMap.get("city"),
                (Country) detailsMap.get("country"),
                (AdvertType) detailsMap.get("advertType"),
                (District) detailsMap.get("district"),
                (User) detailsMap.get("user"));

        updateAdvert.setCategoryPropertyValuesList(advertValueList);

        if (updateAdvert.getCreatedAt() == null) {
            updateAdvert.setCreatedAt(LocalDateTime.now());
        }
        updateAdvert.setUpdatedAt(LocalDateTime.now());

        updateAdvert.setIsActive(advert.getIsActive());
        updateAdvert.setSlug(advert.getSlug());
        updateAdvert.setImagesList(advert.getImagesList());

        Advert returnedAdvert=advertRepository.save(updateAdvert);

        logService.createLogEvent(advert.getUser(),advert, LogEnum.UPDATED);

        return advertMapper.mapAdvertToAdvertResponse(returnedAdvert);
    }

    public AdvertResponse deleteAdvert(Long id, HttpServletRequest request) {
        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.ADMIN, RoleType.MANAGER);
        Advert advert=isAdvertExistById(id);

        if(advert.getBuiltIn()){
            throw new ResourceNotFoundException(ErrorMessages.THIS_ADVERT_DOES_NOT_UPDATE);
        }
        advertRepository.deleteById(id);

        //logService.createLogEvent(advert.getUser(),advert, LogEnum.DELETED);

        return advertMapper.mapAdvertToAdvertResponse(advert);
    }

    public Advert isAdvertExistById(Long id){
        return advertRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_ADVERT_WITH_ID_MESSAGE,id)));
    }


    public List<Advert> getAdvertsReport(String date1, String date2, String category, String type, int status) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
       LocalDateTime begin = LocalDateTime.parse(date1,formatter);
       LocalDateTime end =LocalDateTime.parse(date2,formatter);
       dateTimeValidator.checkBeginTimeAndEndTime(begin,end);

       categoryService.getCategoryByTitle(category);

       /* Status enumStatus;
        try {
            enumStatus= Status.valueOf(status);

        }catch (BadRequestException e){
            throw new BadRequestException(ErrorMessages.ADVERT_STATUS_NOT_FOUND);
        }*/

       int advertStatus =Status.fromValue(status);

        advertTypesService.findByTitle(type);

       return advertRepository.findByQuery(begin,end,category,type,advertStatus).orElseThrow(
                ()-> new BadRequestException(ErrorMessages.NOT_FOUND_ADVERT)
        );

    }

    public Page<Advert> getPopulerAdverts(int amount,Pageable pageable) {

     return advertRepository.getMostPopulerAdverts(amount,pageable);

    }

    @Transactional
    public ResponseEntity<AdvertResponse> trySave(CreateAdvertRequest createRequest, HttpServletRequest request,MultipartFile[] files) {
       methodHelper.getUserByHttpRequest(request);

        Advert advert=advertSet(createRequest);

         List<CategoryPropertyValue>advertValueList=new ArrayList<>();

        for (CreateAdvertPropertyRequest request1 :createRequest.getAdvertPropertyRequest()) {

          advertValueList.add(categoryPropertyValueService.categoryFindByValue(request1.getValue()));
        }

        advert.setCategoryPropertyValuesList(advertValueList);

        Advert savedAdvert =advertRepository.save(advert);

        List<Images> newImageList= methodHelper.getImagesForAdvert(files,savedAdvert.getImagesList());
        savedAdvert.setImagesList(newImageList);
        savedAdvert.generateSlug();

        Advert updatedAdvert =advertRepository.save(savedAdvert);

        return  ResponseEntity.ok(advertMapper.mapAdvertToAdvertResponse(updatedAdvert));


    }

    public Advert advertSet(CreateAdvertRequest createRequest){
        Category category= categoryService.getCategoryById(createRequest.getCategoryId());
        City city=cityService.getCityById(createRequest.getCityId());
        Country country= countryService.getCountryById(createRequest.getCountryId());
        AdvertType advertType=advertTypesService.getAdvertTypeByIdForAdvert(createRequest.getAdvertTypeId());
        District district= districtService.getDistrictByIdForAdvert(createRequest.getDistrictId());
        return advertMapper.mapCreateRequestToAdvert(category,createRequest,city,country,advertType,district);
    }


    public Map<String, Object> getAdvertDetails(AbstractAdvertRequest advertRequest, HttpServletRequest httpServletRequest,Map<String, Object> detailsMap) {

        Category category = categoryService.getCategoryById(advertRequest.getCategoryId());
        City city = cityService.getCityById(advertRequest.getCityId());
        User user = methodHelper.getUserByHttpRequest(httpServletRequest);
        Country country = countryService.getCountryById(advertRequest.getCountryId());
        AdvertType advertType = advertTypesService.getAdvertTypeByIdForAdvert(advertRequest.getAdvertTypeId());
        District district = districtService.getDistrictByIdForAdvert(advertRequest.getDistrictId());

        detailsMap.put("category", category);
        detailsMap.put("city", city);
        detailsMap.put("user", user);
        detailsMap.put("country", country);
        detailsMap.put("advertType", advertType);
        detailsMap.put("district", district);

        return detailsMap;
    }



    @Transactional
    public void resetAdvertTables() {
      //  advertRepository.deleteByBuiltIn(false);

    }

    public Set<Advert> getAdvertsByIdList(Set<Long> advertIdList) {
        return advertRepository.findByIdIn(advertIdList);
    }

    public void saveRunner(Advert advert) {
    advertRepository.save(advert);
    }
}
