package com.cossinest.homes.service.user;


import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.business.Favorites;
import com.cossinest.homes.domain.concretes.business.TourRequest;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.LogEnum;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.exception.MailServiceException;
import com.cossinest.homes.payload.mappers.UserMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.user.*;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import com.cossinest.homes.payload.response.user.SignInResponse;
import com.cossinest.homes.payload.response.user.UserPageableResponse;
import com.cossinest.homes.payload.response.user.UserResponse;
import com.cossinest.homes.repository.user.UserRepository;
import com.cossinest.homes.service.business.AdvertService;
import com.cossinest.homes.service.business.FavoritesService;
import com.cossinest.homes.service.business.LogService;

import com.cossinest.homes.service.business.TourRequestService;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.helper.PageableHelper;
import com.cossinest.homes.service.validator.UserRoleService;
import com.cossinest.homes.utils.MailUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MethodHelper methodHelper;
    private final AdvertService advertService;
    private final TourRequestService tourRequestService;
    // private final FavoritesService favoritesService;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final UserRepository userRepository;
    private final PageableHelper pageableHelper;
    private final EmailService emailService;
    private final LogService logService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticatedUsersResponse getAuthenticatedUser(HttpServletRequest request) {
        User user = methodHelper.getUserByHttpRequest(request);

        return userMapper.authenticatedUsersResponse(user);


    }

    public ResponseEntity<AuthenticatedUsersResponse> updateAuthenticatedUser(AuthenticatedUsersRequest request, HttpServletRequest auth) {
        User user = methodHelper.getUserByHttpRequest(auth);
        methodHelper.checkRoles(user, RoleType.ADMIN, RoleType.MANAGER, RoleType.CUSTOMER);
        if (methodHelper.isBuiltIn(user)) {
            throw new BadRequestException(ErrorMessages.BUILT_IN_USER_CAN_NOT_BE_UPDATED);
        }
        methodHelper.checkUniqueProperties(user, request);
        User updatedUser = userMapper.authenticatedUsersRequestToAutheticatedUser(user, request);
        userRepository.save(updatedUser);

        for (Advert advert : updatedUser.getAdvert()) {

            logService.createLogEvent(updatedUser, advert, LogEnum.USER_UPDATED);

        }

        return ResponseEntity.ok(userMapper.authenticatedUsersResponse(updatedUser));


    }

    public ResponseEntity<String> updateUserPassword(UserPasswordRequest request, HttpServletRequest auth) {
        User user = methodHelper.getUserByHttpRequest(auth);
        if (methodHelper.isBuiltIn(user)) {
            throw new BadRequestException(ErrorMessages.BUILT_IN_USER_CAN_NOT_BE_UPDATED);
        }

        if (!(Objects.equals(request.getNewPassword(), request.getReWriteNewPassword()))) {
            throw new BadRequestException(ErrorMessages.THE_PASSWORDS_ARE_NOT_MATCHED);
        }


        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadRequestException(ErrorMessages.PASSWORD_IS_INCCORECT);
        }

        String password = passwordEncoder.encode(request.getNewPassword());

        user.setPasswordHash(password);
        userRepository.save(user);


        return ResponseEntity.ok(SuccesMessages.PASSWORD_UPDATED_SUCCESSFULLY);
    }

    public String deleteCustomer(HttpServletRequest auth, CustomerRequest request) {
        User user = methodHelper.getUserByHttpRequest(auth);
        if (methodHelper.isBuiltIn(user)) {
            throw new BadRequestException(ErrorMessages.BUILT_IN_USER_CAN_NOT_BE_DELETED);
        }
        methodHelper.checkRoles(user, RoleType.CUSTOMER);
        methodHelper.isRelatedToAdvertsOrTourRequest(user);

        if(request.getEmail()==null){
            throw new BadRequestException("EMAIL IS NULL");
        }

        methodHelper.checkEmailAndPassword(user, request);

        userRepository.delete(user);

        for (Advert advert : user.getAdvert()) {

            logService.createLogEvent(user, advert, LogEnum.USER_DELETED);

        }


        return SuccesMessages.CUSTOMER_DELETED_SUCCESFULLY;


    }


    public ResponseMessage<Page<UserPageableResponse>> getAllAdminAndManagerQueriesByPage(
            HttpServletRequest request, String name, String surname, String email, String phone, int page, int size, String sort, String type) {

        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.ADMIN, RoleType.MANAGER);

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        Page<User> users = userRepository.findAll(name, surname, email, phone, pageable);

        Page<UserPageableResponse> pageableUsers = users.map(userMapper::usersToUserPageableResponse);


        return ResponseMessage.<Page<UserPageableResponse>>builder()
                .status(HttpStatus.OK)
                .message(SuccesMessages.USERS_FETCHED_SUCCESSFULLY)
                .object(pageableUsers)
                .build();

    }


    public ResponseEntity<UserResponse> getUserById(Long id, HttpServletRequest request) {

        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.ADMIN, RoleType.MANAGER);
        UserResponse userResponse = userMapper.userToUserResponse(methodHelper.findUserWithId(id));
        return ResponseEntity.ok(userResponse);


    }


    public ResponseMessage<UserResponse> updateUser(Long id, UsersUpdateRequest request, HttpServletRequest auth) {

        User businessUser = methodHelper.getUserByHttpRequest(auth);
        methodHelper.checkRoles(businessUser, RoleType.MANAGER, RoleType.ADMIN);
        User user = methodHelper.findUserWithId(id);
        if (methodHelper.isBuiltIn(user)) throw new BadRequestException(ErrorMessages.BUILT_IN_USER_CAN_NOT_BE_UPDATED);
        User updatedUser = userMapper.usersUpdateRequestToUser(user, request);

        Set<UserRole>roles=new HashSet<>();

        for (String rol:request.getRoles()) {
            roles.add( userRoleService.getUserRole(RoleType.valueOf(rol)));
        }
        updatedUser.setUserRole(roles);


        for (Advert advert : user.getAdvert()) {

            logService.createLogEvent(user, advert, LogEnum.USER_UPDATED);

        }

        return ResponseMessage.<UserResponse>builder()
                .message(SuccesMessages.USER_UPDATED_SUCCESSFULLY)
                .status(HttpStatus.OK)
                .object(userMapper.userToUserResponse( userRepository.save(updatedUser)))
                .build();


    }

    public UserResponse deleteUserBusiness(Long id, HttpServletRequest auth) {
        User businessUser = methodHelper.getUserByHttpRequest(auth);
        methodHelper.checkRoles(businessUser, RoleType.MANAGER, RoleType.ADMIN);
        User deleteUser = methodHelper.findUserWithId(id);
        if (methodHelper.isBuiltIn(deleteUser)) {
            throw new BadRequestException(ErrorMessages.BUILT_IN_USER_CAN_NOT_BE_DELETED);
        }
        methodHelper.isRelatedToAdvertsOrTourRequest(deleteUser);


        for (Advert advert : deleteUser.getAdvert()) {

            logService.createLogEvent(deleteUser, advert, LogEnum.USER_UPDATED);

        }


        boolean control = false;
        if (businessUser.getUserRole().stream().anyMatch(t -> t.getRoleType().getName().equalsIgnoreCase(RoleType.ADMIN.name()))) {
            control = true;
        } else if (businessUser.getUserRole().stream().anyMatch(t -> t.getRoleName().equalsIgnoreCase(RoleType.MANAGER.name()))) {
            methodHelper.checkRoles(deleteUser, RoleType.CUSTOMER);
            control = true;
        }


        if (control) {
            userRepository.delete(deleteUser);
        } else {
            throw new BadRequestException(ErrorMessages.LOW_ROLE_USERS_CAN_NOT_DELETE_HIGH_ROLE_USERS);
        }

        return userMapper.userToUserResponse(deleteUser);


    }


    public String forgotPassword(ForgetPasswordRequest request) {

        String resetCode;
        try {
            User user = methodHelper.findByUserByEmail(request.getEmail());
            resetCode = UUID.randomUUID().toString();
            user.setResetPasswordCode(resetCode);
            userRepository.save(user);
            MimeMessagePreparator resetPasswordEmail = MailUtil.buildResetPasswordEmail(user.getEmail(),resetCode );
            emailService.sendEmail(resetPasswordEmail);


        } catch (BadRequestException e) {
            return ErrorMessages.THERE_IS_NO_USER_REGISTERED_WITH_THIS_EMAIL_ADRESS;
        }

        return "Code has been sent";

    }

    public ResponseEntity<String> resetPassword(CodeRequest request) {

        User user = userRepository.findByResetPasswordCode(request.getCode()).orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.RESET_CODE_IS_NOT_FOUND, request.getCode())));


        String requestPassword = passwordEncoder.encode(request.getPassword());
        user.setPasswordHash(requestPassword);
        user.setResetPasswordCode(null);
        userRepository.save(user);

        return new ResponseEntity<>(SuccesMessages.PASSWORD_RESET_SUCCESSFULLY, HttpStatus.OK);

    }


    public ResponseEntity<Page<UserPageableResponse>> getAllUsersByPage(HttpServletRequest request, String q, int page, int size, String sort, String type) {
        User user = methodHelper.getUserByHttpRequest(request);
        methodHelper.checkRoles(user, RoleType.ADMIN, RoleType.MANAGER);
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, "asc");
        String query = q != null ? "%" + q.toLowerCase() + "%" : null;
        Page<User> users = userRepository.findAll(query, pageable);

        Page<UserPageableResponse> responsePage = users.map(userMapper::usersToUserPageableResponse);

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    public List<User> getUsersByRoleType(RoleType roleType) {

        // UserRole userRole =userRoleService.getUserRole(roleType);

        return userRepository.findByUserRole_RoleType(roleType);

    }

    public Long countAllAdmins() {

        return userRepository.countAllAdmins(RoleType.ADMIN);

    }

/*    public ResponseEntity<UserResponse> saveUser(UserSaveRequest request,HttpServletRequest servletRequest) {

     User user  = methodHelper.getUserByHttpRequest(servletRequest);
     methodHelper.checkRoles(user,RoleType.ADMIN);

    User savedUser =userMapper.userRequestToUser(request);
     methodHelper.checkDuplicate(savedUser.getEmail(),savedUser.getPhone());



    Set<Advert>adverts =advertService.getAdvertsByIdList(request.getAdvertIdList());
    Set<TourRequest>tourRequests= tourRequestService.getTourRequestsById(request.getTourRequestIdList());
   // List<Favorites>favorites=favoritesService.getFavoritesById(request.getFavoritesList());

    savedUser.setAdvert(adverts);
    savedUser.setTourRequests(tourRequests);
 //   savedUser.setFavoritesList(favorites);

    //TODO Password encoder
    savedUser.setPasswordHash(request.getPassword());



    //methodHelper.
   // savedUser.setUserRole();



     userRepository.save(savedUser);

     return ResponseEntity.ok(userMapper.userToUserResponse(savedUser));


    }*/

    @Transactional
    public ResponseEntity<UserResponse> saveUserWithoutRequest(UserSaveRequest request) {


        methodHelper.checkDuplicate(request.getEmail(), request.getPhone());
        User savedUser = userMapper.userRequestToUser(request);

       /* Set<Advert>adverts =advertService.getAdvertsByIdList(request.getAdvertIdList());
        Set<TourRequest>tourRequests= tourRequestService.getTourRequestsById(request.getTourRequestIdList());
        List<Favorites>favorites=favoritesService.getFavoritesById(request.getFavoritesList());
        savedUser.setFavoritesList(favorites);*/

        //TODO Password encoder
        savedUser.setPasswordHash(request.getPassword());

        //   savedUser.setAdvert(adverts);
        //   savedUser.setTourRequests(tourRequests);


        Set<UserRole> userRolesSaved = new HashSet<>();
        userRolesSaved.add(userRoleService.getUserRole(RoleType.ADMIN));
        savedUser.setUserRole(userRolesSaved);


        userRepository.save(savedUser);

        return ResponseEntity.ok(userMapper.userToUserResponse(savedUser));


    }

    public ResponseEntity<SignInResponse> registerUser(SignInRequest signInRequest) {
        // Duplicate email & phone kontrolü
        methodHelper.checkDuplicate(signInRequest.getEmail(), signInRequest.getPhone());

        // Yeni kullanıcı oluşturma
        User newUser = new User();
        newUser.setFirstName(signInRequest.getFirstName());
        newUser.setLastName(signInRequest.getLastName());
        newUser.setEmail(signInRequest.getEmail());
        newUser.setPhone(signInRequest.getPhone());
        newUser.setPasswordHash(passwordEncoder.encode(signInRequest.getPassword()));

        // Roller için boş bir set oluşturma
        Set<UserRole> roles = new HashSet<>();

        // Eğer roller null veya boş ise, varsayılan olarak CUSTOMER rolü eklenir
        if (signInRequest.getRole() == null || signInRequest.getRole().isEmpty()) {
            UserRole defaultRole = userRoleService.getUserRole(RoleType.CUSTOMER);
            if (defaultRole != null) {
                roles.add(defaultRole);
            }
        } else { // Eğer roller null değilse, içindeki her rol kontrol edilir
            for (RoleType roleType : signInRequest.getRole()) {
                UserRole userRole = userRoleService.getUserRole(roleType);
                if (userRole != null) {
                    roles.add(userRole);
                }
            }
        }

        // Kullanıcının rollerini ayarlama
        newUser.setUserRole(roles);

        // Kullanıcıyı veritabanına kaydetme
        User registeredUser = userRepository.save(newUser);

        // Başarılı yanıt döndürme

        try {
            MimeMessagePreparator registrationEmail = MailUtil.buildRegistrationEmail(registeredUser.getEmail());
            emailService.sendEmail(registrationEmail);
        } catch (Exception e) {
            throw new MailServiceException(e.getMessage());
        }

        SignInResponse response= userMapper.userToSignInResponse(registeredUser);
        return new ResponseEntity(response , HttpStatus.CREATED);

    }



/*    @Transactional
    public void resetUserTables() {

        userRepository.deleteByBuiltIn(false);
    }*/
    }

