package com.cossinest.homes.service.helper;

import com.cossinest.homes.domain.concretes.business.*;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.NotLoadingCompleted;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.request.abstracts.AbstractAdvertRequest;
import com.cossinest.homes.payload.request.business.AdvertRequest;
import com.cossinest.homes.payload.request.business.AdvertRequestForAdmin;
import com.cossinest.homes.payload.request.user.AuthenticatedUsersRequest;
import com.cossinest.homes.payload.request.user.CustomerRequest;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import com.cossinest.homes.repository.business.AdvertRepository;
import com.cossinest.homes.repository.user.UserRepository;
import com.cossinest.homes.service.business.AdvertService;
import com.cossinest.homes.service.business.CategoryPropertyValueService;
import com.cossinest.homes.service.business.TourRequestService;
import com.cossinest.homes.service.validator.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.management.relation.Role;
import javax.swing.text.html.parser.Entity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;
    private final AdvertService advertService;
    private final TourRequestService tourRequestService;

    public User findByUserByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_EMAIL, email)));
    }

    public String getEmailByRequest(HttpServletRequest request) {
        return (String) request.getAttribute("email");
    }

    public boolean isBuiltIn(User user) {

        return user.getBuilt_in();
    }


    public User getUserByHttpRequest(HttpServletRequest request) {
        return findByUserByEmail(getEmailByRequest(request));

    }


    public void checkDuplicate(String email, String phone) {

        if (userRepository.existsByEmail()) {
            throw new ConflictException(String.format(ErrorMessages.THIS_EMAIL_IS_ALREADY_TAKEN, email));
        }
        if (userRepository.existsByPhone()) {
            throw new ConflictException(String.format(ErrorMessages.THIS_PHONE_NUMBER_IS_ALREADY_TAKEN, phone));

        }

    }


    public void checkUniqueProperties(User user, AuthenticatedUsersRequest request) {

        boolean changed = false;
        String changedEmail = "";
        String changedPhone = "";

        if (user.getEmail().equalsIgnoreCase(request.getEmail())) {
            changed = true;
            changedEmail = request.getEmail();
        }

        if (user.getPhone().equalsIgnoreCase(request.getPhone())) {
            changed = true;
            changedPhone = request.getEmail();
        }

        if (changed) {
            checkDuplicate(changedEmail, changedPhone);
        }


    }


    public void checkEmailAndPassword(User user, CustomerRequest request) {

        if (!(user.getEmail().equals(request.getEmail())))
            throw new BadRequestException(String.format(ErrorMessages.EMAIL_IS_INCORRECT, request.getEmail()));
        if (!(Objects.equals(user.getPasswordHash(), request.getPassword())))
            throw new BadRequestException(ErrorMessages.PASSWORD_IS_NOT_CORRECT);

    }

    public User findUserWithId(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.USER_IS_NOT_FOUND, id)));
    }


    public void checkRoles(User user, RoleType... roleTypes) {

        Set<RoleType> roles = new HashSet<>();
        Collections.addAll(roles, roleTypes);

        for (UserRole userRole : user.getUserRole()) {
            if (roles.contains(userRole.getRoleType())) return;
        }
        throw new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND);
    }


    public Set<UserRole> roleStringToUserRole(Set<String> request) {

        return request.stream().map(item -> userRoleService.getUserRole(RoleType.valueOf(item))).collect(Collectors.toSet());
    }


    public void controlRoles(User user, RoleType... roleTypes) {

        Set<RoleType> roles = new HashSet<>();
        Collections.addAll(roles, roleTypes);
        Set<UserRole> rolesUserRole = roles.stream().map(userRoleService::getUserRole).collect(Collectors.toSet());

        for (UserRole role : user.getUserRole()) {
            if (!(rolesUserRole.contains(role))) {
                throw new BadRequestException(ErrorMessages.NOT_HAVE_AUTHORITY);
            }
        }
    }

    public void UpdatePasswordControl(String password, String reWritePassword) {
        if (!Objects.equals(password, reWritePassword)) {
            throw new BadRequestException(ErrorMessages.PASSWORDS_DID_NOT_MATCH);
        }
    }


    //Advert
    public int calculatePopularityPoint(int advertTourRequestListSize, int advertViewCount) {
        return (3 * advertTourRequestListSize) + advertViewCount;
    }


    public boolean priceControl(Double startPrice, Double endPrice) {
        if (startPrice < 0 || endPrice < startPrice || endPrice < 0) {
            return true;
        } else return false;
    }

    public Map<Object, Object> mapTwoListToOneMap(List<Object> list1, List<Object> list2) {
        Map<Object, Object> resultMap = new LinkedHashMap<>();

        for (int i = 0; i < Math.min(list1.size(), list2.size()); i++) {
            resultMap.put(list1.get(i), list2.get(i));
        }

        return resultMap;
    }


    public List<CategoryPropertyValue> getPropertyValueList(Category category, AbstractAdvertRequest advertRequest, CategoryPropertyValueService categoryPropertyValueService) {
        //adım:1==>Db den category e ait PropertyKeyleri getir
        List<CategoryPropertyKey> categoryPropertyKeys = category.getCategoryPropertyKeys();
        //adım:2==>gelen PropertyKeyleri idleri ile yeni bir liste oluştur
        List<Long> cpkIds = categoryPropertyKeys.stream().map(t -> t.getId()).collect(Collectors.toList());
        //adım:3==>requestten gelen properti ile map yapısı oluştur
        List<Object> propertyKeys = advertRequest.getProperties().stream().map(t -> t.get("keyId")).collect(Collectors.toList());
        List<Object> propertyValues = advertRequest.getProperties().stream().map(t -> t.get("value")).collect(Collectors.toList());
        Map<Object, Object> propertyKeyAndPropertyValue = mapTwoListToOneMap(propertyKeys, propertyValues);
        //adım:4==>yeni bir liste oluştur ve dbden kelen keylerin içerisinde requestten gelen key varsa mapten o objenin valuesunu yeni listeye koy
        List<Object> propertyForAdvert = new ArrayList<>();
        propertyKeys.stream().map(t -> cpkIds.contains(t) ? propertyForAdvert.add(propertyKeyAndPropertyValue.get(t)) : null);//value birden fazla gelebilir

        //adım:5==>artık elimde valuelar olan bir dizi var bu dizinin elamanlarını kullanarak db den propertyvalue ları çağır advertın içine ata
        return propertyForAdvert.stream()
                .map(t -> categoryPropertyValueService.getCategoryPropertyValueForAdvert(t)).collect(Collectors.toList());
    }

    public void getPropertiesForAdvertResponse(CategoryPropertyValue categoryPropertyValue, CategoryPropertyValueService categoryPropertyValueService, Map<String, String> propertyNameAndValue) {
        String propertyKeyName = categoryPropertyValueService.getPropertyKeyNameByPropertyValue(categoryPropertyValue.getId());
        String propertyValue = categoryPropertyValue.getValue();
        propertyNameAndValue.put(propertyKeyName, propertyValue);
    }


    public Map<String, String> getAdvertResponseProperties(Advert advert, CategoryPropertyValueService categoryPropertyValueService) {
        Map<String, String> properties = new HashMap<>();
        for (int i = 0; i < advert.getCategoryPropertyValuesList().size(); i++) {
            getPropertiesForAdvertResponse(advert.getCategoryPropertyValuesList().get(i), categoryPropertyValueService, properties);
        }
        return properties;
    }

    public User getUserAndCheckRoles(HttpServletRequest request, String name) {
        User user = getUserByHttpRequest(request);
        checkRoles(user, RoleType.valueOf(name));
        return user;
    }



    public <T> ResponseEntity<byte[]> excelResponse(List<T> list) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("AdvertReport");
            int rowNum = 0;

            if (!list.isEmpty() && list.get(0) instanceof User) {


                for (User fetchedUser : (List<User>) list) {

                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(fetchedUser.getId());
                    row.createCell(1).setCellValue(fetchedUser.getFirstName());
                    row.createCell(2).setCellValue(fetchedUser.getLastName());
                }


            } else if (!list.isEmpty() && list.get(0) instanceof TourRequest) {


                for (TourRequest tourRequest : (List<TourRequest>) list) {

                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(tourRequest.getId());
                    row.createCell(1).setCellValue(tourRequest.getOwnerUserId().getFirstName());
                    row.createCell(2).setCellValue(tourRequest.getAdvertId().getTitle());
                }


            } else if (!list.isEmpty() && list.get(0) instanceof Advert) {
                for (Advert advert : (List<Advert>) list) {

                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(advert.getId());
                    row.createCell(1).setCellValue(advert.getTitle());
                    row.createCell(2).setCellValue(advert.getStatus());
                    row.createCell(3).setCellValue(advert.getAdvertType().getTitle());
                    row.createCell(4).setCellValue(advert.getCategory().getTitle());
                }

            }
            else if (!list.isEmpty() && list.get(0) instanceof Advert) {
                for (Advert advert : (Page<Advert>) list) {

                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(advert.getId());
                    row.createCell(1).setCellValue(advert.getTitle());
                    row.createCell(2).setCellValue(advert.getStatus());
                    row.createCell(3).setCellValue(advert.getAdvertType().getTitle());
                    row.createCell(4).setCellValue(advert.getCategory().getTitle());
                }

            }


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "report.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

        } catch (IOException e) {
           throw new BadRequestException("ERROR");
        }
    }


    public <T> ResponseEntity<byte[]> excelResponse(Page<T> list) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet=workbook.createSheet("AdvertReport");
            int rowNum = 0;

            for (Advert advert :(Page<Advert>) list) {

                Row row=sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(advert.getId());
                row.createCell(1).setCellValue(advert.getTitle());
                row.createCell(2).setCellValue(advert.getStatus());
                row.createCell(3).setCellValue(advert.getAdvertType().getTitle());
                row.createCell(4).setCellValue(advert.getCategory().getTitle());
            }

            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "report.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(outputStream.toByteArray(),headers, HttpStatus.OK);


        } catch (BadRequestException | IOException err) {
            throw new BadRequestException("ERR");
        }

    }


    public List<Images> getImagesForAdvert(MultipartFile[] files,List<Images> images){
        boolean isFirstImage = true;
        for (MultipartFile file:files) {

            try{
                Images image = new Images();

                image.setData(file.getBytes());
                image.setName(file.getOriginalFilename());
                image.setType(file.getContentType());

                if(isFirstImage){
                    image.setFeatured(true);
                    isFirstImage=false;
                }else{
                    image.setFeatured(false);
                }

                images.add(image);

            }catch(IOException e){
                throw  new NotLoadingCompleted(ErrorMessages.UPLOADING_FAILED);
            }
        }
        return images;
    }


    public List<Long> getImagesIdsListForAdvert(List<Images> imagesList){
        List<Long> imagesIdsList= new ArrayList<>();

        imagesList.stream().map(t->imagesIdsList.add(t.getId())).collect(Collectors.toList());
        return imagesIdsList;
    }


    // Category

    public boolean builtIn(Category category) {

        return category.getBuiltIn();
    }

    public boolean isActive(Category category){

        return category.getActive();
    }


    // CategoryPropertyKey

    public boolean builtIn(CategoryPropertyKey categoryPropertyKey) {

        return categoryPropertyKey.getBuiltIn();

    }


    public void isRelatedToAdvertsOrTourRequest(User user) {

        if(user.getTourRequests().size()>0 || user.getAdvert().size()>0) {
            throw new BadRequestException(ErrorMessages.THE_USER_HAS_RELATED_RECORDS_WITH_ADVERTS_OR_TOUR_REQUESTS);
        }

    }
}


