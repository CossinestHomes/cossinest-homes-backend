package com.cossinest.homes.service.user;


import com.cossinest.homes.domain.concretes.business.Advert;
import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.LogEnum;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.payload.mappers.UserMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.user.*;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import com.cossinest.homes.payload.response.user.UserPageableResponse;
import com.cossinest.homes.payload.response.user.UserResponse;
import com.cossinest.homes.repository.user.UserRepository;
import com.cossinest.homes.service.business.LogService;

import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.helper.PageableHelper;
import com.cossinest.homes.service.validator.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MethodHelper methodHelper;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final UserRepository userRepository;
  //  private final PasswordEncoder passwordEncoder;
    private final PageableHelper pageableHelper;
    private final EmailService emailService;
    private final LogService logService;

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

      /*  String password = passwordEncoder.encode(request.getPassword());
        if (!(Objects.equals(password, user.getPasswordHash()))) {
            throw new BadRequestException(ErrorMessages.PASSWORD_IS_INCCORECT);
        }
        if (!(Objects.equals(request.getNewPassword(), request.getReWriteNewPassword()))) {
            throw new BadRequestException(ErrorMessages.THE_PASSWORDS_ARE_NOT_MATCHED);
        }*/

       // user.setPasswordHash(password);
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

      //  String requestPassword = passwordEncoder.encode(request.getPassword());
      //  request.setPassword(requestPassword);

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


        for (Advert advert : user.getAdvert()) {

            logService.createLogEvent(user, advert, LogEnum.USER_UPDATED);

        }

        return ResponseMessage.<UserResponse>builder()
                .message(SuccesMessages.USER_UPDATED_SUCCESSFULLY)
                .status(HttpStatus.OK)
                .object(userMapper.userToUserResponse(updatedUser))
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


   /* public String forgotPassword(ForgetPasswordRequest request) {

        String resetCode;
        try {
            User user = methodHelper.findByUserByEmail(request.getEmail());
             resetCode = UUID.randomUUID().toString();
            user.setResetPasswordCode(resetCode);
            userRepository.save(user);
         //   emailService.sendEmail(user.getEmail(), "Reset email", "Your reset email code is:" + resetCode);

        } catch (BadRequestException e) {
            return ErrorMessages.THERE_IS_NO_USER_REGISTERED_WITH_THIS_EMAIL_ADRESS;
        }

        return resetCode;

    }*/

  /*public ResponseEntity<String> resetPassword(ResetCodeRequest request) {

        User user =userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new BadRequestException(ErrorMessages.NOT_FOUND_USER_EMAIL));


        methodHelper.UpdatePasswordControl(request.getPassword(), request.getReWritePassword());
    //    String requestPassword = passwordEncoder.encode(request.getPassword());
      //  user.setPasswordHash(requestPassword);
        userRepository.save(user);
        return new ResponseEntity<>(SuccesMessages.PASSWORD_RESET_SUCCESSFULLY, HttpStatus.OK);

    }*/

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

/*    @Transactional
    public void resetUserTables() {

        userRepository.deleteByBuiltIn(false);
    }*/
}
