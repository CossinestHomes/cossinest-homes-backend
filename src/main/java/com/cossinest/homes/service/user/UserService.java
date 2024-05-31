package com.cossinest.homes.service.user;


import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.payload.mappers.UserMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.user.AuthenticatedUsersRequest;
import com.cossinest.homes.payload.request.user.CustomerRequest;
import com.cossinest.homes.payload.request.user.UserPasswordRequest;
import com.cossinest.homes.payload.request.user.UsersUpdateRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import com.cossinest.homes.payload.response.user.UserPageableResponse;
import com.cossinest.homes.payload.response.user.UserResponse;
import com.cossinest.homes.repository.user.UserRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.helper.PageableHelper;
import com.cossinest.homes.service.validator.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final PasswordEncoder passwordEncoder;
    private final PageableHelper pageableHelper;

    public AuthenticatedUsersResponse getAuthenticatedUser(HttpServletRequest request) {
        User user = methodHelper.getUserByHttpRequest(request);

        return userMapper.authenticatedUsersResponse(user);


    }

    public ResponseEntity<AuthenticatedUsersResponse> updateAuthenticatedUser(AuthenticatedUsersRequest request, HttpServletRequest auth) {
        User user = methodHelper.getUserByHttpRequest(auth);
        methodHelper.checkRoles(user,RoleType.ADMIN,RoleType.MANAGER,RoleType.CUSTOMER);
        if (methodHelper.isBuiltIn(user)) {
            throw new BadRequestException(ErrorMessages.BUILT_IN_USER_CAN_NOT_BE_UPDATED);
        }
        methodHelper.checkUniqueProperties(user, request);
        User updatedUser = userMapper.authenticatedUsersRequestToAutheticatedUser(user, request);
        userRepository.save(updatedUser);

        return ResponseEntity.ok(userMapper.authenticatedUsersResponse(updatedUser));


    }

    public ResponseEntity<String> updateUserPassword(UserPasswordRequest request, HttpServletRequest auth) {
        User user = methodHelper.getUserByHttpRequest(auth);
        if (methodHelper.isBuiltIn(user)) {
            throw new BadRequestException(ErrorMessages.BUILT_IN_USER_CAN_NOT_BE_UPDATED);
        }

        String password = passwordEncoder.encode(request.getPassword());
        if (!(Objects.equals(password, user.getPasswordHash()))) {
            throw new BadRequestException(ErrorMessages.PASSWORD_IS_INCCORECT);
        }
        if (!(Objects.equals(request.getNewPassword(), request.getReWriteNewPassword()))) {
            throw new BadRequestException(ErrorMessages.THE_PASSWORDS_ARE_NOT_MATCHED);
        }

        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);


        return ResponseEntity.ok(SuccesMessages.PASSWORD_UPDATED_SUCCESSFULLY);
    }

    public String deleteCustomer(HttpServletRequest auth, CustomerRequest request) {
        User user = methodHelper.getUserByHttpRequest(auth);
        if (methodHelper.isBuiltIn(user)) {
            throw new BadRequestException(ErrorMessages.BUILT_IN_USER_CAN_NOT_BE_DELETED);
        }
        methodHelper.checkRoles(user,RoleType.CUSTOMER);

        String requestPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(requestPassword);

        methodHelper.checkEmailAndPassword(user, request);

        userRepository.delete(user);

        return SuccesMessages.CUSTOMER_DELETED_SUCCESFULLY;


    }


    public ResponseMessage<Page<UserPageableResponse>> getAllAdminAndManagerByPage(
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

    //TODO add FAVORI AND LOG CustomerResponse
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
        if(methodHelper.isBuiltIn(user)) throw new BadRequestException(ErrorMessages.BUILT_IN_USER_CAN_NOT_BE_UPDATED);
        User updatedUser=userMapper.usersUpdateRequestToUser(user,request);


        return ResponseMessage.<UserResponse>builder()
                .message(SuccesMessages.USER_UPDATED_SUCCESSFULLY)
                .status(HttpStatus.OK)
                .object(userMapper.userToUserResponse(updatedUser))
                .build();


    }

    public UserResponse deleteUserBusiness(Long id, HttpServletRequest auth) {
       User businessUser =methodHelper.getUserByHttpRequest(auth);
       methodHelper.checkRoles(businessUser,RoleType.MANAGER,RoleType.ADMIN);
       User deleteUser=methodHelper.findUserWithId(id);
       if(methodHelper.isBuiltIn(deleteUser)) throw new BadRequestException(ErrorMessages.BUILT_IN_USER_CAN_NOT_BE_DELETED);

       if(businessUser.getUserRole().stream().anyMatch(t->t.getRoleType().getName().equalsIgnoreCase(RoleType.ADMIN.name()))){
           userRepository.delete(deleteUser);
       }else if(businessUser.getUserRole().stream().anyMatch(t->t.getRoleName().equalsIgnoreCase(RoleType.MANAGER.name()))){
            try{
                methodHelper.checkRoles(deleteUser,RoleType.CUSTOMER);
                userRepository.delete(deleteUser);
            }
            catch (BadRequestException e){
                throw new BadRequestException(ErrorMessages.LOW_ROLE_USERS_CAN_NOT_DELETE_HIGH_ROLE_USERS);
            }

       }

       return userMapper.userToUserResponse(deleteUser);


    }
}
