package com.cossinest.homes.service.helper;

import com.cossinest.homes.domain.concretes.business.*;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.domain.enums.Status;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.NotLoadingCompleted;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.request.user.AuthenticatedUsersRequest;
import com.cossinest.homes.payload.request.user.CustomerRequest;
import com.cossinest.homes.repository.business.FavoritesRepository;
import com.cossinest.homes.repository.user.UserRepository;

import com.cossinest.homes.service.business.CategoryPropertyValueService;
import com.cossinest.homes.service.validator.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;




    public User findByUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new ResourceNotFoundException("Email can not be null or empty");
        }

        return userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_EMAIL, email)));
    }

    public String getEmailByRequest(HttpServletRequest request) {
        return (String) request.getAttribute("email");
    }

    public boolean isBuiltIn(User user) {

        return user.getBuiltIn();
    }


    public User getUserByHttpRequest(HttpServletRequest request) {
        return findByUserByEmail(getEmailByRequest(request));

    }


    public void checkDuplicate(String email, String phone) {

        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(ErrorMessages.THIS_EMAIL_IS_ALREADY_TAKEN, email));
        }
        if (userRepository.existsByPhone(phone)) {
            throw new ConflictException(String.format(ErrorMessages.THIS_PHONE_NUMBER_IS_ALREADY_TAKEN, phone));

        }

    }


    public void checkUniqueProperties(User user, AuthenticatedUsersRequest request) {

        boolean changed = false;
        String changedEmail = "";
        String changedPhone = "";

        if (!user.getEmail().equalsIgnoreCase(request.getEmail())) {
            changed = true;
            changedEmail = request.getEmail();
        }

        if (!user.getPhone().equalsIgnoreCase(request.getPhone())) {
            changed = true;
            changedPhone = request.getEmail();
        }

        if (changed) {
            checkDuplicate(changedEmail, changedPhone);
        }


    }


    public void checkEmailAndPassword(User user, CustomerRequest request) {



        if (!user.getEmail().equals(request.getEmail())){
            throw new BadRequestException(String.format(ErrorMessages.EMAIL_IS_INCORRECT, request.getEmail()));
        }

        if (!passwordEncoder.matches(request.getPassword(),user.getPasswordHash())){
            throw new BadRequestException(ErrorMessages.PASSWORD_IS_NOT_CORRECT);
        }
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



    public void getPropertiesForAdvertResponse(CategoryPropertyValue categoryPropertyValue, CategoryPropertyValueService categoryPropertyValueService, Map<String, String> propertyNameAndValue) {
        String propertyKeyName = categoryPropertyValueService.getPropertyKeyNameByPropertyValue(categoryPropertyValue.getId());
        String propertyValue = categoryPropertyValue.getValue();
        propertyNameAndValue.put(propertyKeyName, propertyValue);
        //  categoryPropertyValue.getCategoryPropertyKeys().getName();
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


    public static Long getUserIdFromRequest(HttpServletRequest httpServletRequest, UserRepository userRepository) {

        String email = (String) httpServletRequest.getAttribute("email");


        Optional<User> userOptional = userRepository.findByEmail(email); // TODO Optional yerine throw new olabilir


        return userOptional.map(User::getId).orElse(null);
    }

    public static void addFavorite(User user, Advert advert, FavoritesRepository favoritesRepository) {
        // Favori ilanın var olup olmadığını kontrol et
        boolean isFavorite = favoritesRepository.existsByUserIdAndAdvertId(user.getId(), advert.getId());

        // Eğer ilgili favori zaten yoksa, favori ekle
        if (!isFavorite) {
            Favorites favorite = new Favorites();
            favorite.setUser(user);
            favorite.setAdvert(advert);
            favoritesRepository.save(favorite);
        }
    }


    private void createRow(Sheet sheet, int rowNum,CellStyle style, Object... values) {
        Row row = sheet.createRow(rowNum);
        for (int i = 0; i < values.length; i++) {

            Cell cell= row.createCell(i);
            cell.setCellValue(values[i].toString());
            if (style != null) {
                cell.setCellStyle(style);
            }

        }
    }

    private HttpHeaders returnHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "report.xlsx");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return headers;
    }


    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    public <T> ResponseEntity<byte[]> excelResponse(List<T> list) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("AdvertReport");
            int rowNum = 0;

            CellStyle headerStyle = createHeaderStyle(workbook);


            if (!list.isEmpty() && list.get(0) instanceof User) {
                createRow(sheet, rowNum++, headerStyle,"ID", "Name", "Last Name","Email","Phone");
                for (User fetchedUser : (List<User>) list) {

                    createRow(sheet, rowNum++,null, fetchedUser.getId(), fetchedUser.getFirstName(), fetchedUser.getLastName(),fetchedUser.getEmail(),fetchedUser.getPhone());
                }
            } else if (!list.isEmpty() && list.get(0) instanceof TourRequest) {
                createRow(sheet, rowNum++, headerStyle,"ID", "Name", "Last Name","Title");

                for (TourRequest tourRequest : (List<TourRequest>) list) {
                    createRow(sheet, rowNum++,null, tourRequest.getId(), tourRequest.getOwnerUser().getFirstName(),tourRequest.getOwnerUser().getLastName(), tourRequest.getAdvert().getTitle());
                }
            } else if (!list.isEmpty() && list.get(0) instanceof Advert) {
                //TODO hem advert hemde advertType title var ikisinide gerek var mi?
                createRow(sheet, rowNum++, headerStyle,"ID", "AdvertTitle", "Status","AdvertTypeTitle","CategoryTitle");

                for (Advert advert : (List<Advert>) list) {
                    createRow(sheet, rowNum++,null, advert.getId(), advert.getTitle(), advert.getStatus(), advert.getAdvertType().getTitle(), advert.getCategory().getTitle());
                }

            }
           else{
                throw new BadRequestException(ErrorMessages.EXCEL_COULD_NOT_BE_CREATED);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            HttpHeaders headers =returnHeader();
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

        } catch (IOException e) {
            throw new BadRequestException("ERROR");
        }
    }


    public <T> ResponseEntity<byte[]> excelResponse(Page<T> list) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("AdvertReport");
            int rowNum = 0;
            List<T> page = list.getContent();
            CellStyle headerStyle = createHeaderStyle(workbook);

            if (page.isEmpty() || !(page.get(0) instanceof Advert)){
                throw new BadRequestException(ErrorMessages.EXCEL_COULD_NOT_BE_CREATED_TYPE_IS_NOT_ADVERT);
            }
            createRow(sheet, rowNum++, headerStyle,"ID", "AdvertTitle", "Status","AdvertTypeTitle","CategoryTitle");

            for (Advert advert : (Page<Advert>) page) {
                createRow(sheet,rowNum++,null, advert.getId(),advert.getTitle(),advert.getStatus(),advert.getAdvertType().getTitle(),advert.getCategory().getTitle());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            HttpHeaders headers =returnHeader();

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);


        } catch (BadRequestException | IOException err) {
            throw new BadRequestException("ERR");
        }

    }


    public List<Images> getImagesForAdvert(MultipartFile[] files, List<Images> images) {

        if (images == null) {
            images = new ArrayList<>();
        }

        boolean isFirstImage = true;
        for (MultipartFile file : files) {

            try {
                Images image = new Images();

                image.setData(file.getBytes());
                image.setName(file.getOriginalFilename());
                image.setType(file.getContentType());

                if (isFirstImage) {
                    image.setFeatured(true);
                    isFirstImage = false;
                } else {
                    image.setFeatured(false);
                }

                images.add(image);

            } catch (IOException e) {
                throw new NotLoadingCompleted(ErrorMessages.UPLOADING_FAILED);
            }
        }
        return images;
    }


    public List<Long> getImagesIdsListForAdvert(List<Images> imagesList) {
        List<Long> imagesIdsList = new ArrayList<>();

        if (imagesList != null) {
            imagesList.stream()
                    .filter(Objects::nonNull)
                    .map(Images::getId)
                    .forEach(imagesIdsList::add);
        }

        return imagesIdsList;
    }


    // Category

    public boolean builtIn(Category category) {

        return category.getBuiltIn();
    }

    public boolean isActive(Category category) {

        return category.getActive();
    }


    // CategoryPropertyKeyType

    public boolean builtIn(CategoryPropertyKey categoryPropertyKey) {

        return categoryPropertyKey.getBuiltIn();

    }


    public void isRelatedToAdvertsOrTourRequest(User user) {

        if (user.getTourRequests().size() > 0 || user.getAdvert().size() > 0) {
            throw new BadRequestException(ErrorMessages.THE_USER_HAS_RELATED_RECORDS_WITH_ADVERTS_OR_TOUR_REQUESTS);
        }

    }

    public int updateAdvertStatus(int caseNumber, Advert advert) {
        Status status;
        switch (caseNumber) {
            case 0:
                status = Status.PENDING;
                advert.setIsActive(false);
                System.out.println("Advert status set to PENDING. Advert is now inactive.");
                break;
            case 1:
                status = Status.ACTIVATED;
                advert.setIsActive(true);
                System.out.println("Advert status set to ACTIVATED. Advert is now active.");
                break;
            case 2:
                status = Status.REJECTED;
                advert.setIsActive(false);
                System.out.println("Advert status set to REJECTED. Advert is inactive.");
                break;
            default:
                System.out.println("Invalid case number.");
                return Status.PENDING.getValue();
        }
        return caseNumber;
    }



}



