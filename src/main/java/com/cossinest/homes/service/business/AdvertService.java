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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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


    public Page<AdvertResponse> getAllAdvertsByPage(String query, Long categoryId, Long advertTypeId, Double priceStart, Double priceEnd, String location, Integer status, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        if (methodHelper.priceControl(priceStart, priceEnd)) {
            throw new ConflictException(ErrorMessages.START_PRICE_AND_END_PRICE_INVALID);
        }

        return advertRepository.findByAdvertByQuery(categoryId, advertTypeId, priceStart, priceEnd, status, location, query, pageable)
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

    public Page<AdvertResponse> getAllAdvertsByPageForAdmin(HttpServletRequest request,String query ,Long categoryId, Long advertTypeId, Double priceStart, Double priceEnd, String location, int status, int page, int size, String sort, String type) {
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
        getAdvertDetails(advertRequest,httpServletRequest,detailsMap);


        Advert advert =advertMapper.mapAdvertRequestToAdvert(
                advertRequest,
                (Category) detailsMap.get("category"),
                (City) detailsMap.get("city"),
                (User) detailsMap.get("user"),
                (Country) detailsMap.get("country"),
                (AdvertType) detailsMap.get("advertType"),
                (District) detailsMap.get("district"));

        List<CategoryPropertyValue> categoryPropertyValuesForDb =methodHelper.getPropertyValueList((Category) detailsMap.get("category"),advertRequest,categoryPropertyValueService);
        advert.setCategoryPropertyValuesList(categoryPropertyValuesForDb);
        advert.setImagesList(methodHelper.getImagesForAdvert(files,advert.getImagesList()));//TODO:image setleme kontrol et

        logService.createLogEvent(advert.getUser(),advert, LogEnum.CREATED);


        Advert savedAdvert = advertRepository.save(advert);
        savedAdvert.generateSlug();
        advertRepository.save(savedAdvert);


        return advertMapper.mapAdvertToAdvertResponse(advert);

    }

    @Transactional
    public AdvertResponse updateUsersAdvert(AdvertRequest advertRequest, Long id, HttpServletRequest request) {
        User user = methodHelper.getUserAndCheckRoles(request,RoleType.CUSTOMER.name());
        Advert advert=isAdvertExistById(id);
        if(advert.getUser().getId() != user.getId()){
            throw new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_IS_NOT_FOUND_FOR_USER,user.getId()));
        }
        if(advert.getBuiltIn()){
            throw new ResourceNotFoundException(ErrorMessages.THIS_ADVERT_DOES_NOT_UPDATE);
        }
        Map<String, Object> detailsMap = new HashMap<>();
        getAdvertDetails(advertRequest,request,detailsMap);

        List<CategoryPropertyValue> categoryPropertyValuesForDb =methodHelper.getPropertyValueList((Category) detailsMap.get("category"),advertRequest,categoryPropertyValueService);

        Advert updateAdvert =advertMapper.mapAdvertRequestToUpdateAdvert(id,advertRequest,
                (Category) detailsMap.get("category"),
                (City) detailsMap.get("city"),
                (Country) detailsMap.get("country"),
                (AdvertType) detailsMap.get("advertType"),
                (District) detailsMap.get("district"));
        updateAdvert.setUser(user);
        updateAdvert.setCategoryPropertyValuesList(categoryPropertyValuesForDb);

        Advert returnedAdvert=advertRepository.save(updateAdvert); //TODO Entitye PostUpdate Anatasyon eklendi
      //  returnedAdvert.generateSlug();
      //  Advert updatedAdvert = advertRepository.save(returnedAdvert);


        logService.createLogEvent(advert.getUser(),advert, LogEnum.UPDATED);

        return advertMapper.mapAdvertToAdvertResponse(returnedAdvert);
    }

    @Transactional
    public AdvertResponse updateAdvert(AdvertRequestForAdmin advertRequest, Long id, HttpServletRequest request) {
        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.ADMIN, RoleType.MANAGER);
        Advert advert=isAdvertExistById(id);

        if(advert.getBuiltIn()){
            throw new ResourceNotFoundException(ErrorMessages.THIS_ADVERT_DOES_NOT_UPDATE);
        }

        Map<String, Object> detailsMap = new HashMap<>();
        getAdvertDetails(advertRequest,request,detailsMap);

        List<CategoryPropertyValue> categoryPropertyValuesForDb =methodHelper.getPropertyValueList((Category) detailsMap.get("category"),advertRequest,categoryPropertyValueService);

        Advert updateAdvert =advertMapper.mapAdvertRequestToUpdateAdvert(id,advertRequest,
                (Category) detailsMap.get("category"),
                (City) detailsMap.get("city"),
                (Country) detailsMap.get("country"),
                (AdvertType) detailsMap.get("advertType"),
                (District) detailsMap.get("district"));

        updateAdvert.setCategoryPropertyValuesList(categoryPropertyValuesForDb);

        Advert returnedAdvert=advertRepository.save(updateAdvert);
       // returnedAdvert.generateSlug();
       // Advert updatedAdvert = advertRepository.save(returnedAdvert);

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

        logService.createLogEvent(advert.getUser(),advert, LogEnum.DELETED);

        return advertMapper.mapAdvertToAdvertResponse(advert);
    }

    public Advert isAdvertExistById(Long id){
        return advertRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_ADVERT_WITH_ID_MESSAGE,id)));
    }


    public List<Advert> getAdvertsReport(String date1, String date2, String category, String type, String status) {

       DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
       LocalDateTime begin =LocalDateTime.parse(date1+" 00:00:00",formatter);
       LocalDateTime end =LocalDateTime.parse(date2+" 23:59:59",formatter);
       dateTimeValidator.checkBeginTimeAndEndTime(begin,end);

       categoryService.getCategoryByTitle(category);

        Status enumStatus;
        try {
            enumStatus= Status.valueOf(status);

        }catch (BadRequestException e){
            throw new BadRequestException(ErrorMessages.ADVERT_STATUS_NOT_FOUND);
        }

       return advertRepository.findByQuery(begin,end,category,type,enumStatus ).orElseThrow(
                ()-> new BadRequestException(ErrorMessages.NOT_FOUND_ADVERT)
        );

    }

    public Page<Advert> getPopulerAdverts(int amount,Pageable pageable) {

     return advertRepository.getMostPopulerAdverts(amount,pageable);

    }

    @Transactional
    public ResponseEntity<AdvertResponse> trySave(CreateAdvertRequest createRequest, HttpServletRequest request) {
       methodHelper.getUserByHttpRequest(request);

        Advert advert=advertSet(createRequest);

         List<CategoryPropertyValue>advertValueList=new ArrayList<>();

        for (CreateAdvertPropertyRequest request1 :createRequest.getAdvertPropertyRequest()) {

          advertValueList.add(categoryPropertyValueService.categoryFindByValue(request1.getValue()));
        }

        advert.setCategoryPropertyValuesList(advertValueList);

        Advert savedAdvert =advertRepository.save(advert);

        List<Images> newImageList= methodHelper.getImagesForAdvert(createRequest.getFiles(),savedAdvert.getImagesList());
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

}
